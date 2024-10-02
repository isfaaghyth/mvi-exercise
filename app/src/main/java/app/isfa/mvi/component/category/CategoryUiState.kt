package app.isfa.mvi.component.category

import app.isfa.mvi.base.UiState

data class CategoryUiState(
    val items: List<String>,
    val state: UiState
) {

    companion object {
        val Default get() = CategoryUiState(
            items = listOf(),
            state = UiState.Loading
        )
    }
}