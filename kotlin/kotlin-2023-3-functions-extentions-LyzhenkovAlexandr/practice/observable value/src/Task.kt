interface Cancellation {
    fun cancel()
}

interface Value<T> {
    val value: T
    fun observe(observer: (T) -> Unit): Cancellation
}

class MutableValue<T>(initial: T) : Value<T> {
    private val observers = mutableListOf<(T) -> Unit>()
    override var value: T = initial
        set(newValue) {
            field = newValue
            notifyObservers()
        }

    override fun observe(observer: (T) -> Unit): Cancellation {
        observers.add(observer)
        observer(value)
        return object : Cancellation {
            override fun cancel() {
                observers.remove(observer)
            }
        }
    }

    private fun notifyObservers() {
        observers.forEach { it(value) }
    }
}
