package airline.api

import kotlinx.datetime.Instant

data class Flight(
    val flightId: String,
    val departureTime: Instant,
    val isCancelled: Boolean = false,
    val actualDepartureTime: Instant = departureTime,
    val checkInNumber: String? = null,
    val gateNumber: String? = null,
    val plane: Plane,
    val tickers: MutableMap<String, Ticket> = mutableMapOf(),
)

fun Flight.takeRegistrationOpeningTime(conf: AirlineConfig) = this.actualDepartureTime - conf.registrationOpeningTime

fun Flight.takeRegistrationClosingTime(conf: AirlineConfig) = this.actualDepartureTime - conf.registrationClosingTime

fun Flight.takeBoardingOpeningTime(conf: AirlineConfig) = this.actualDepartureTime - conf.boardingOpeningTime

fun Flight.takeBoardingClosingTime(conf: AirlineConfig) = this.actualDepartureTime - conf.boardingClosingTime
