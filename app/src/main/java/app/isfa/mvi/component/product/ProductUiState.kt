package app.isfa.mvi.component.product

import app.isfa.mvi.base.UiState

data class ProductUiState(
    val items: List<Pair<Int, String>>,
    val state: UiState
) {

    companion object {
        val Default get() = ProductUiState(
            items = emptyList(),
            state = UiState.Loading
        )
    }
}