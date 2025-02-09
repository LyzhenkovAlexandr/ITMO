package airline.api

import kotlinx.datetime.Instant

interface FlightMessage : Message {
    data class ScheduleFlight(
        val flightId: String,
        val departureTime: Instant,
        val plane: Plane,
    ) : FlightMessage

    data class DelayFlight(
        val flightId: String,
        val departureTime: Instant,
        val actualDepartureTime: Instant,
    ) : FlightMessage

    data class CancelFlight(
        val flightId: String,
        val departureTime: Instant,
    ) : FlightMessage

    data class UpdateCheckInNumberFlight(
        val flightId: String,
        val departureTime: Instant,
        val checkInNumber: String,
    ) : FlightMessage

    data class UpdateGateNumberFlight(
        val flightId: String,
        val departureTime: Instant,
        val gateNumber: String,
    ) : FlightMessage

    data class BuyTicket(
        val flightId: String,
        val departureTime: Instant,
        val seatNo: String,
        val passengerId: String,
        val passengerName: String,
        val passengerEmail: String,
    ) : FlightMessage
}
