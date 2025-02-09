package airline.servies

import airline.api.FlightMessage
import airline.api.Message
import airline.api.Ticket

object MessageGenerator {
    fun changedParametersFlight(ticket: Ticket, msg: Message): String {
        return when (msg) {
            is FlightMessage.DelayFlight -> {
                "Dear, ${ticket.passengerName}. Unfortunately, your flight ${ticket.flightId} delayed from " +
                    "${ticket.departureTime} to ${msg.actualDepartureTime}."
            }

            is FlightMessage.CancelFlight -> {
                "Dear, ${ticket.passengerName}. Unfortunately, your flight ${ticket.flightId} has been cancelled."
            }

            is FlightMessage.UpdateCheckInNumberFlight -> {
                "Dear, ${ticket.passengerName}. The check-in counter for your flight ${ticket.flightId} has been " +
                    "changed to ${msg.checkInNumber}."
            }

            is FlightMessage.UpdateGateNumberFlight -> {
                "Dear, ${ticket.passengerName}. The gate number for your flight ${ticket.flightId} has been changed " +
                    "to ${msg.gateNumber}."
            }

            else -> throw IllegalArgumentException("There is no message for this change")
        }
    }
}
