val Int.milliseconds: Time
    get() = Time((this / 1000).toLong(), this % 1000)

val Long.milliseconds: Time
    get() = Time(this / 1000, (this % 1000).toInt())

val Int.seconds: Time
    get() = Time(this.toLong(), 0)

val Long.seconds: Time
    get() = Time(this, 0)

val Int.minutes: Time
    get() = Time(this.toLong() * 60L, 0)

val Long.minutes: Time
    get() = Time(this * 60L, 0)

val Int.hours: Time
    get() = Time(this.toLong() * 3600L, 0)

val Long.hours: Time
    get() = Time(this * 3600L, 0)


operator fun Time.plus(other: Time): Time {
    val seconds = this.seconds + other.seconds
    val milliSeconds = this.milliseconds + other.milliseconds
    val overFlowSeconds = milliSeconds / 1000
    return Time(seconds + overFlowSeconds, milliSeconds % 1000)
}

operator fun Time.minus(other: Time): Time {
    val seconds = this.seconds - other.seconds
    val milliSeconds = this.milliseconds - other.milliseconds
    if (milliSeconds >= 0) {
        return Time(seconds, milliSeconds)
    }
    return Time(seconds - 1, 1000 + milliSeconds)
}

operator fun Time.times(factor: Int): Time {
    val seconds = this.seconds * factor
    val milliseconds = this.milliseconds * factor
    val overflowSeconds = milliseconds / 1000
    return Time(seconds + overflowSeconds, milliseconds % 1000)
}
