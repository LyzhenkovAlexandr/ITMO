package airline.api

interface PassengerMessage : Message {
    data class SendAllPassenger(val flight: Flight, val typeMessage: Message) : PassengerMessage
}
