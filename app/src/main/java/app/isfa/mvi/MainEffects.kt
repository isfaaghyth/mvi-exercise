package app.isfa.mvi

sealed interface MainEffects

data class ShowNetworkError(val message: String) : MainEffects