package chatbot.dsl

import chatbot.api.*

@DslMarker
annotation class BotDSL

@BotDSL
class BotBuilder(private val client: Client) : Builder<ChatBot> {
    private var logLevel: LogLevel = LogLevel.ERROR
    private var contextManager: ChatContextsManager? = null
    private val order: MutableList<Action> = mutableListOf()
    private val orderGeneric: MutableList<ActionGeneric<ChatContext?>> = mutableListOf()
    private val behaviour = BehaviourBuilder(order, orderGeneric, contextManager)

    fun use(contextsManager: ChatContextsManager) {
        this.contextManager = contextsManager
    }

    fun use(level: LogLevel) {
        this.logLevel = level
    }

    operator fun LogLevel.unaryPlus() {
        use(this)
    }

    fun behaviour(builder: BehaviourBuilder<ChatContext?>.() -> Unit) {
        behaviour.into<ChatContext?>(builder)
        behaviour.builder()
    }

    override fun build(): ChatBot {
        return Bot(client, logLevel, behaviour.build(), contextManager)
    }
}

class Bot(
    private val client: Client,
    private val log: LogLevel,
    private val behaviour: Behaviour<ChatContext?>,
    private var contextManager: ChatContextsManager?,
) : ChatBot {
    override val logLevel: LogLevel
        get() = log

    override fun processMessages(message: Message) {
        val chatContext = contextManager?.getContext(message.chatId)
        val context = MessageProcessorContext(message, client, chatContext) { c ->
            contextManager?.setContext(message.chatId, c)
        }
        behaviour.run {
            changeManager(contextManager)
            starter(context)
        }
    }
}

fun chatBot(client: Client, builder: BotBuilder.() -> Unit): ChatBot = BotBuilder(client).apply(builder).build()
internal fun chatBot(client: Client): ChatBot = BotBuilder(client).build()
