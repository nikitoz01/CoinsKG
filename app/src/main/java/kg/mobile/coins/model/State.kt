package kg.mobile.coins.model

sealed class State<out T> {
    object Loading : State<Nothing>()
    data class Success<out T>(val value: T) : State<T>()
    data class Fail(val exception: Throwable) : State<Nothing>()

    fun isFail() = this is Fail
    fun isSuccess() = this is Success
    fun isLoading() = this is Loading
}