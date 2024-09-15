package dc.danh.tyme.code.challenge.data.repository

/**
 * Wraps an API call in a try-catch block and returns a [Result] object.
 *
 * This function helps handle exceptions thrown during API calls and provides a consistent way to return
 * either a successful result or an error.
 *
 * @param T The type of the data returned by the API call.
 * @param apiCall The suspend function that makes the API call.
 * @return A [Result] object representing either a [Result.Success] with the data or a [Result.Error] with the exception.
 */
abstract class BaseRepository {
    protected suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
        return try {
            Result.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            Result.Error(throwable)
        }
    }
}

/**
 * A sealed class representing the result of an operation.
 *
 * The operation can either be a [Success] with a value of type [T],
 * or an [Error] with an [exception].
 */
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
}
