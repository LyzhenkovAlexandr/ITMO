package airline.servies

import airline.api.FlightMessage
import airline.api.Plane
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.datetime.Instant

class AirlineManagement(private val updatesFlow: MutableSharedFlow<FlightMessage>) : AirlineManagementService {

    override suspend fun scheduleFlight(flightId: String, departureTime: Instant, plane: Plane) {
        updatesFlow.emit(FlightMessage.ScheduleFlight(flightId, departureTime, plane))
    }

    override suspend fun delayFlight(flightId: String, departureTime: Instant, actualDepartureTime: Instant) {
        updatesFlow.emit(FlightMessage.DelayFlight(flightId, departureTime, actualDepartureTime))
    }

    override suspend fun cancelFlight(flightId: String, departureTime: Instant) {
        updatesFlow.emit(FlightMessage.CancelFlight(flightId, departureTime))
    }

    override suspend fun setCheckInNumber(flightId: String, departureTime: Instant, checkInNumber: String) {
        updatesFlow.emit(FlightMessage.UpdateCheckInNumberFlight(flightId, departureTime, checkInNumber))
    }

    override suspend fun setGateNumber(flightId: String, departureTime: Instant, gateNumber: String) {
        updatesFlow.emit(FlightMessage.UpdateGateNumberFlight(flightId, departureTime, gateNumber))
    }
}
