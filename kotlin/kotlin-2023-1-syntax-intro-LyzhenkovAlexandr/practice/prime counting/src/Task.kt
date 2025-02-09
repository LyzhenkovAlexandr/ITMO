import kotlin.math.sqrt


fun isPrime(n: Int): Boolean {
    if (n < 2) {
        return false
    }
    val root = sqrt(n.toDouble()).toInt()
    if (n == root * root) {
        return false
    }

    for (d in 2..root) {
        if (n % d == 0) {
            return false
        }
    }
    return true
}

fun piFunction(x: Double): Int {
    var count = 0
    for (num in 2..x.toInt()) {
        if (isPrime(num)) {
            count++
        }
    }
    return count
}
