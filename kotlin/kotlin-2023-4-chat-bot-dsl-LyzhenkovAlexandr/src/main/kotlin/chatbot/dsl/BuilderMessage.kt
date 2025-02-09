package chatbot.dsl

import chatbot.api.Keyboard
import chatbot.api.MessageId

class BuilderMessage {
    internal var statusKeyboard: Keyboard? = null
    var text: String = ""
    var replyTo: MessageId? = null
    private val keyboardBuilder: KeyboardBuilder = KeyboardBuilder()

    fun removeKeyboard() {
        statusKeyboard = Keyboard.Remove
    }

    fun withKeyboard(function: KeyboardBuilder.() -> Unit) {
        keyboardBuilder.function()
        statusKeyboard = Keyboard.Markup(keyboardBuilder.oneTime, keyboardBuilder.keyboard)
    }

    internal fun isEmptyKeyboard(): Boolean {
        return keyboardBuilder.isEmptyKeyboard()
    }
}

class KeyboardBuilder {
    var keyboard: MutableList<MutableList<Keyboard.Button>> = mutableListOf()
    var oneTime: Boolean = false
    private val rowBuilder: RowBuilder = RowBuilder(keyboard)

    fun row(function: RowBuilder.() -> Unit) {
        keyboard += mutableListOf<Keyboard.Button>()
        rowBuilder.function()
    }

    internal fun isEmptyKeyboard(): Boolean {
        keyboard.forEach { row -> if (row.isNotEmpty()) return false }
        return true
    }
}

class RowBuilder(
    private val keyboard: MutableList<MutableList<Keyboard.Button>>,
) {
    fun button(text: String) {
        keyboard.last() += Keyboard.Button(text)
    }

    operator fun String.unaryMinus() {
        button(this)
    }
}
