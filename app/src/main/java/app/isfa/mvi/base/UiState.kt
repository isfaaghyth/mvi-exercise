package app.isfa.mvi.base

sealed class UiState {
    data object Success : UiState()
    data object Loading : UiState()
    data class Fail(val error: Throwable?) : UiState()

    val isSuccess: Boolean
        get() = this == Success

    val isLoading: Boolean
        get() = this == Loading

    val isFail: Boolean
        get() = this is Fail
}