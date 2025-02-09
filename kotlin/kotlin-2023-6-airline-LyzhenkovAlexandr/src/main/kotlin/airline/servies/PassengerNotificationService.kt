package airline.servies

import airline.api.Flight
import airline.api.Message

interface PassengerNotificationService {
    suspend fun sendPassengerMessage(flight: Flight, typeMessage: Message)
}
