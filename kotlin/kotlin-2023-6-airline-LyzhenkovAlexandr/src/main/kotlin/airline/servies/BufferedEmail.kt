package airline.servies

import airline.api.EmailMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class BufferedEmail(private val emailService: EmailService) : EmailService {
    private val messageChannel = Channel<EmailMessage>(capacity = Channel.BUFFERED)
    override suspend fun send(to: String, text: String) {
        messageChannel.send(EmailMessage.SendOnEmail(to, text))
    }

    suspend fun runBufferedEmail() = coroutineScope {
        for (action in messageChannel) {
            when (action) {
                is EmailMessage.SendOnEmail -> {
                    launch(Dispatchers.IO) {
                        emailService.send(action.to, action.text)
                    }
                }
            }
        }
    }
}
