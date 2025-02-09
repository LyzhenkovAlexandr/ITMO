fun greet(name: String): String = "Hello, $name!"

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println(greet(readlnOrNull() ?: "Anonymous"))
        return
    }
    args.forEach { arg -> println(greet(arg)) }
}
