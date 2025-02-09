package chatbot.dsl

import chatbot.api.*

@BotDSL
class MessageProcessorContext<C : ChatContext?>(
    val message: Message,
    val client: Client,
    val context: C,
    val setContext: (c: ChatContext?) -> Unit,
) {
    fun sendMessage(chatId: ChatId, builder: BuilderMessage.() -> Unit) {
        val settings = BuilderMessage().apply(builder)
        val statKeyboard = settings.statusKeyboard
        val text = settings.text
        if (text.isBlank() && (statKeyboard == null || settings.isEmptyKeyboard()) && statKeyboard != Keyboard.Remove) {
            return
        }
        client.sendMessage(chatId, text, settings.statusKeyboard, settings.replyTo)
    }
}

typealias MessageProcessor<C> = MessageProcessorContext<C>.() -> Unit
