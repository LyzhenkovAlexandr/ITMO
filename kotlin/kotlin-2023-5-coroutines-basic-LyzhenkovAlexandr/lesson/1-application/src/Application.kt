import kotlinx.coroutines.*

fun CoroutineScope.runApplication(
    runUI: suspend () -> Unit,
    runApi: suspend () -> Unit,
) {
    val apiJob = launch {
        while (isActive) {
            try {
                runApi()
                break
            } catch (e: Exception) {
                delay(1000)
            }
        }
    }
    launch {
        try {
            runUI()
        } catch (e: Exception) {
            apiJob.cancelAndJoin()
            throw e
        }
    }
}
