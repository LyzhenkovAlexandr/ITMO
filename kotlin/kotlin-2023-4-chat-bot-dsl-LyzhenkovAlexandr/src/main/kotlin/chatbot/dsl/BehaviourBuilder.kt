package chatbot.dsl

import chatbot.api.ChatBot
import chatbot.api.ChatContext
import chatbot.api.ChatContextsManager
import chatbot.api.Message

@BotDSL
class BehaviourBuilder<T : ChatContext?>(
    var order: MutableList<Action>,
    private val orderGeneric: MutableList<ActionGeneric<T>>,
    internal var manager: ChatContextsManager?,
) : Builder<Behaviour<T>> {

    fun onCommand(command: String, function: MessageProcessor<T>) {
        pushToOrder(function) { context ->
            context.message.text.startsWith("/$command")
        }
    }

    fun onMessage(predicate: ChatBot.(Message) -> Boolean, function: MessageProcessor<T>) {
        pushToOrder(function) { context ->
            predicate(chatBot(context.client), context.message)
        }
    }

    fun onMessagePrefix(prefix: String, function: MessageProcessor<T>) {
        pushToOrder(function) { context ->
            context.message.text.startsWith(prefix)
        }
    }

    fun onMessageContains(text: String, function: MessageProcessor<T>) {
        pushToOrder(function) { context ->
            text in context.message.text
        }
    }

    fun onMessage(messageTextExactly: String, function: MessageProcessor<T>) {
        pushToOrder(function) { context ->
            context.message.text == messageTextExactly
        }
    }

    fun onMessage(function: MessageProcessor<T>) {
        pushToOrder(function) { true }
    }

    inline fun <reified T : ChatContext?> into(builder: BehaviourBuilder<T>.() -> Unit) {
        doInto<T>(builder)
    }

    inline infix fun <reified T : ChatContext?> T.into(builder: BehaviourBuilder<T>.() -> Unit) {
        doInto<T>(builder)
    }

    inline infix fun <reified T : ChatContext?> doInto(builder: BehaviourBuilder<T>.() -> Unit) {
        val newOrder = mutableListOf<ActionGeneric<T>>()
        val obj = BehaviourBuilder(mutableListOf(), newOrder, null).apply(builder)
        order += obj.order
        newOrder.forEach { pushGenericActionToOrder(it) }
    }

    inline fun <reified T : ChatContext?> pushGenericActionToOrder(action: ActionGeneric<T>) {
        order += Action { context ->
            val curContext = context.context
            if (curContext is T) {
                val proc = MessageProcessorContext(context.message, context.client, curContext, context.setContext)
                if (action.cond(proc)) {
                    action.function(proc)
                    return@Action true
                }
            }
            false
        }
    }

    private fun pushToOrder(
        function: MessageProcessor<T>,
        cond: (MessageProcessorContext<T>) -> Boolean,
    ) {
        orderGeneric += ActionGeneric(cond, function)
    }

    override fun build(): Behaviour<T> {
        return Behaviour(order, this)
    }
}

class Behaviour<T : ChatContext?>(
    private val order: MutableList<Action>,
    private val builderBehaviour: BehaviourBuilder<T>,
) {
    internal fun starter(context: MessageProcessorContext<ChatContext?>) {
        order.forEach { (cond) ->
            if (cond(context)) {
                return
            }
        }
    }

    internal fun changeManager(manager: ChatContextsManager?) {
        builderBehaviour.manager = manager
    }
}
