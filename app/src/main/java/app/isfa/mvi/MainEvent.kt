package app.isfa.mvi

sealed interface MainEvent

data class ProductItemClicked(val name: String) : MainEvent