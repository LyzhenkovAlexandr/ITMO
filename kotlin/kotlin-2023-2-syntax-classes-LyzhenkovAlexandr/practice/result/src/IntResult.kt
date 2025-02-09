class NoResultProvided(message: String) : NoSuchElementException(message)
sealed interface IntResult {
    fun getOrDefault(defaultValue: Int): Int = when (this) {
        is Ok -> value
        is Error -> defaultValue
    }

    fun getOrNull(): Int? = when (this) {
        is Ok -> value
        is Error -> null
    }

    fun getStrict(): Int = when (this) {
        is Ok -> value
        is Error -> throw NoResultProvided(reason)
    }

    data class Ok(val value: Int) : IntResult
    data class Error(val reason: String) : IntResult
}

fun safeRun(f: () -> Int): IntResult = try {
    IntResult.Ok(f())
} catch (e: Throwable) {
    IntResult.Error(e.message ?: "error")
}
