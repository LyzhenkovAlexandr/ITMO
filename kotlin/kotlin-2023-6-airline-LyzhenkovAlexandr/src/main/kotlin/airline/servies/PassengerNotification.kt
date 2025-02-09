package airline.servies

import airline.api.Flight
import airline.api.Message
import airline.api.PassengerMessage
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class PassengerNotification(private val bufferedEmailService: EmailService) : PassengerNotificationService {
    private val passengerNotificationChannel = Channel<PassengerMessage>(capacity = Channel.BUFFERED)
    override suspend fun sendPassengerMessage(flight: Flight, typeMessage: Message) {
        passengerNotificationChannel.send(PassengerMessage.SendAllPassenger(flight, typeMessage))
    }

    suspend fun runPassengerNotification() = coroutineScope {
        for (action in passengerNotificationChannel) {
            when (action) {
                is PassengerMessage.SendAllPassenger -> {
                    launch {
                        action.flight.tickers.forEach { (_, ticket) ->
                            val message = MessageGenerator.changedParametersFlight(ticket, action.typeMessage)
                            bufferedEmailService.send(ticket.passengerEmail, message)
                        }
                    }
                }
            }
        }
    }
}
