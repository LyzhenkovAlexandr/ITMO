package airline.servies

import airline.api.Flight
import airline.api.FlightInfo
import airline.api.FlightMessage
import airline.findIndexFlight
import kotlin.time.Duration
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class Booking(
    private val updatesFlow: MutableSharedFlow<FlightMessage>,
    private val flights: StateFlow<List<Flight>>,
    private val ticketSaleEndTime: Duration,
) : BookingServices {
    override val flightSchedule: List<FlightInfo>
        get() {
            val result = mutableListOf<FlightInfo>()
            flights.value.forEach { flight ->
                val freeSeats = freeSeats(flight.flightId, flight.departureTime)
                if (!flight.isCancelled && freeSeats.isNotEmpty() &&
                    Clock.System.now() < flight.departureTime - ticketSaleEndTime
                ) {
                    result += FlightInfo(
                        flight.flightId, flight.departureTime, false, flight.actualDepartureTime,
                        flight.checkInNumber, flight.gateNumber, flight.plane,
                    )
                }
            }
            return result.toList()
        }

    override fun freeSeats(flightId: String, departureTime: Instant): Set<String> {
        val result = mutableSetOf<String>()
        val listNowFlights = flights.value
        val existingFlightIndex = findIndexFlight(listNowFlights, flightId, departureTime)
        if (existingFlightIndex != -1) {
            val purchasedSeats = mutableSetOf<String>()
            val flight = listNowFlights[existingFlightIndex]
            flight.tickers.forEach { (_, ticket) ->
                purchasedSeats += ticket.seatNo
            }
            result += flight.plane.seats - purchasedSeats
        }
        return result.toSet()
    }

    override suspend fun buyTicket(
        flightId: String,
        departureTime: Instant,
        seatNo: String,
        passengerId: String,
        passengerName: String,
        passengerEmail: String,
    ) {
        updatesFlow.emit(
            FlightMessage.BuyTicket(
                flightId,
                departureTime,
                seatNo,
                passengerId,
                passengerName,
                passengerEmail,
            ),
        )
    }
}
