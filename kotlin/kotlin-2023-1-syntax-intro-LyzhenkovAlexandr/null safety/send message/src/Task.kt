fun sendMessageToClient(
    client: Client?,
    message: String?,
    mailer: Mailer
) {
    val email = client?.personalInfo?.email ?: return
    val currentMessage = message ?: "Hello!"
    mailer.sendMessage(email, currentMessage)
}
