import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin

class ParallelEvaluator {
    suspend fun run(task: Task, n: Int, context: CoroutineContext) {
        val deferredList = mutableListOf<Deferred<Unit>>()
        repeat(n) {
            val deferred = CoroutineScope(context).async {
                task.run(it)
            }
            try {
                deferred.await()
            } catch (e: Exception) {
                deferredList.forEach { it.cancelAndJoin() }
                throw TaskEvaluationException(e)
            }
            deferredList.add(deferred)
        }
    }
}
