package chatbot.dsl

interface Builder<T> {
    fun build(): T
}
