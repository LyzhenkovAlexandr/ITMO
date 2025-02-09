import java.io.InputStream
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class Config {
    private val data: Map<String, String>

    constructor(config: String) {
        this.data = getData(config)
    }

    operator fun provideDelegate(thisRef: Any?, property: KProperty<*>): ReadOnlyProperty<Any?, String> {
        val key = property.name
        require(key in data) { "Key '$key' not found" }
        return ReadOnlyProperty { _, _ -> data.getValue(key) }
    }

    companion object {
        private fun getData(config: String): Map<String, String> {
            val source = getResource(config)
            requireNotNull(source) { "Config source unavailable" }

            val data = mutableMapOf<String, String>()
            source.bufferedReader().forEachLine { line ->
                if (line.isBlank()) {
                    return@forEachLine
                }
                val args = line.split("=")
                require(args.size == 2) { "'$line' - unexpected line appearance" }
                val (key, value) = args.map { it.trim() }
                data[key] = value
            }
            return data
        }
    }
}
