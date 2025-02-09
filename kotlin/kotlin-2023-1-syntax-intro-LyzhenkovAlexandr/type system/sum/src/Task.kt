fun sum(a: Any, b: Any): Any? {
    return if (a is Int && b is Int) {
        a + b
    } else if (a is Long && b is Long) {
        a + b
    } else if (a is String && b is String) {
        a + b
    } else if (a is Boolean && b is Boolean) {
        a or b
    } else {
        null
    }
}
