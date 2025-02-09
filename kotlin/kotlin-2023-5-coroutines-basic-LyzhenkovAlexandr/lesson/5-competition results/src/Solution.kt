import kotlin.time.Duration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

fun Flow<Cutoff>.resultsFlow(): Flow<Results> {
    val currentResults = mutableMapOf<String, Duration>()

    return transform { cutoff ->
        currentResults[cutoff.number] = cutoff.time
        emit(Results(currentResults.toMap()))
    }
}
