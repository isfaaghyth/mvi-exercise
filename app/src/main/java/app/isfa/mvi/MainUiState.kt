package app.isfa.mvi

import app.isfa.mvi.component.product.ProductUiState

data class MainUiState(
    val productUiState: ProductUiState
) {

    companion object {
        val Empty get() = MainUiState(
            productUiState = ProductUiState.Default
        )
    }
}
