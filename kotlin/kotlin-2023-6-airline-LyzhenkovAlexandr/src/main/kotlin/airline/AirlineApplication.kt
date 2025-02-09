package airline

import airline.api.*
import airline.servies.*
import kotlin.time.Duration.Companion.minutes
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class AirlineApplication(private val config: AirlineConfig, emailService: EmailService) {
    private val updatesFlow = MutableSharedFlow<FlightMessage>(extraBufferCapacity = 1000000)
    private val flights = MutableStateFlow<List<Flight>>(emptyList())

    val bookingService: BookingServices = Booking(updatesFlow, flights, config.ticketSaleEndTime)
    val managementService: AirlineManagementService = AirlineManagement(updatesFlow)
    val bufferedEmailService: BufferedEmail = BufferedEmail(emailService)
    val passengerNotificationService: PassengerNotification = PassengerNotification(bufferedEmailService)

    @OptIn(FlowPreview::class)
    fun airportInformationDisplay(coroutineScope: CoroutineScope): StateFlow<InformationDisplay> {
        return flights.map { flights ->
            InformationDisplay(
                flights.map { flight ->
                    FlightInfo(
                        flight.flightId,
                        flight.departureTime,
                        flight.isCancelled,
                        flight.actualDepartureTime,
                        flight.checkInNumber,
                        flight.gateNumber,
                        flight.plane,
                    )
                },
            )
        }
            .sample(config.displayUpdateInterval)
            .stateIn(
                scope = coroutineScope,
                started = SharingStarted.Eagerly,
                initialValue = InformationDisplay(emptyList()),
            )
    }

    val airportAudioAlerts: Flow<AudioAlerts> = flow {
        flights.value.forEach { flight ->
            val registrationOpeningTime = flight.takeRegistrationOpeningTime(config)
            val registrationClosingTime = flight.takeRegistrationClosingTime(config)
            val boardingOpeningTime = flight.takeBoardingOpeningTime(config)
            val boardingClosingTime = flight.takeBoardingClosingTime(config)
            val now = Clock.System.now()

            if (flight.checkInNumber != null) {
                if (now in registrationOpeningTime..registrationOpeningTime + 3.minutes) {
                    emit(AudioAlerts.RegistrationOpen(flight.flightId, flight.checkInNumber))
                }
                if (now in registrationClosingTime - 3.minutes..registrationClosingTime) {
                    emit(AudioAlerts.RegistrationClosing(flight.flightId, flight.checkInNumber))
                }
            }
            if (flight.gateNumber != null) {
                if (now in boardingOpeningTime..boardingOpeningTime + 3.minutes) {
                    emit(AudioAlerts.BoardingOpened(flight.flightId, flight.gateNumber))
                }
                if (now in boardingClosingTime - 3.minutes..boardingClosingTime) {
                    emit(AudioAlerts.BoardingClosing(flight.flightId, flight.gateNumber))
                }
            }
        }
        delay(config.audioAlertsInterval)
    }

    suspend fun run() {
        coroutineScope {
            launch {
                updatesFlow.collect { msg ->
                    when (msg) {
                        is FlightMessage.ScheduleFlight -> {
                            val existingFlightIndex = findIndexFlight(flights.value, msg.flightId, msg.departureTime)
                            if (existingFlightIndex == -1) {
                                val newFlightList = flights.value.toMutableList()
                                newFlightList += Flight(msg.flightId, msg.departureTime, plane = msg.plane)
                                flights.emit(newFlightList.toList())
                            }
                        }

                        is FlightMessage.DelayFlight -> {
                            changeFlightParameters(msg.flightId, msg.departureTime, msg) { list, index ->
                                list[index].copy(actualDepartureTime = msg.actualDepartureTime)
                            }
                        }

                        is FlightMessage.CancelFlight -> {
                            changeFlightParameters(msg.flightId, msg.departureTime, msg) { list, index ->
                                list[index].copy(isCancelled = true)
                            }
                        }

                        is FlightMessage.UpdateCheckInNumberFlight -> {
                            changeFlightParameters(msg.flightId, msg.departureTime, msg) { list, index ->
                                list[index].copy(checkInNumber = msg.checkInNumber)
                            }
                        }

                        is FlightMessage.UpdateGateNumberFlight -> {
                            changeFlightParameters(msg.flightId, msg.departureTime, msg) { list, index ->
                                list[index].copy(gateNumber = msg.gateNumber)
                            }
                        }

                        is FlightMessage.BuyTicket -> {
                            var message =
                                "Failed to buy a ticket: this flight ${msg.flightId} is not available " +
                                    "or the seat ${msg.seatNo} " +
                                    "is not available for purchase"
                            val flightIndex = findIndexFlight(flights.value, msg.flightId, msg.departureTime)
                            if (flightIndex != -1) {
                                val flightFromFlights = flights.value[flightIndex]
                                val curFlightInfo = FlightInfo(
                                    flightFromFlights.flightId,
                                    flightFromFlights.departureTime,
                                    flightFromFlights.isCancelled,
                                    flightFromFlights.actualDepartureTime,
                                    flightFromFlights.checkInNumber,
                                    flightFromFlights.gateNumber,
                                    flightFromFlights.plane,
                                )
                                if (curFlightInfo in bookingService.flightSchedule) {
                                    val freeSeats = bookingService.freeSeats(msg.flightId, msg.departureTime)
                                    if (msg.seatNo in freeSeats) {
                                        val oldFlightList = flights.value.toMutableList()
                                        val existingFlightIndex =
                                            findIndexFlight(oldFlightList, msg.flightId, msg.departureTime)
                                        val ticket = Ticket(
                                            msg.flightId,
                                            msg.departureTime,
                                            msg.seatNo,
                                            msg.passengerId,
                                            msg.passengerName,
                                            msg.passengerEmail,
                                        )
                                        oldFlightList[existingFlightIndex].tickers[msg.passengerId] = ticket
                                        flights.emit(oldFlightList.toList())
                                        message =
                                            "Ticket for flight ${msg.flightId} seat ${msg.seatNo} successfully bought"
                                    }
                                }
                            }
                            bufferedEmailService.send(msg.passengerEmail, message)
                        }
                    }
                }
            }

            launch { bufferedEmailService.runBufferedEmail() }

            launch { passengerNotificationService.runPassengerNotification() }
        }
    }

    private suspend fun changeFlightParameters(
        flightId: String,
        departureTime: Instant,
        typeMessage: FlightMessage,
        copy: (list: List<Flight>, index: Int) -> Flight,
    ) {
        val oldFlightList = flights.value.toMutableList()
        val existingFlightIndex = findIndexFlight(oldFlightList, flightId, departureTime)
        if (existingFlightIndex != -1) {
            val updatedFlight = copy(oldFlightList, existingFlightIndex)
            oldFlightList[existingFlightIndex] = updatedFlight
            flights.emit(oldFlightList.toList())
            passengerNotificationService.sendPassengerMessage(updatedFlight, typeMessage)
        }
    }
}

fun findIndexFlight(listFlights: List<Flight>, flightId: String, departureTime: Instant): Int {
    return listFlights.indexOfFirst {
        it.flightId == flightId && it.departureTime == departureTime
    }
}
