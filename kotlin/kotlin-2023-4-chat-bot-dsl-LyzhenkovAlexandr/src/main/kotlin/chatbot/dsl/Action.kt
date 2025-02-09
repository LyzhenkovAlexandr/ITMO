package chatbot.dsl

import chatbot.api.ChatContext

data class Action(
    val cond: (MessageProcessorContext<ChatContext?>) -> Boolean,
)

data class ActionGeneric<T : ChatContext?>(
    val cond: (MessageProcessorContext<T>) -> Boolean,
    val function: MessageProcessor<T>,
)
