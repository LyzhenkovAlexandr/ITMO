package airline.api

sealed interface EmailMessage : Message {
    data class SendOnEmail(val to: String, val text: String) : EmailMessage
}
