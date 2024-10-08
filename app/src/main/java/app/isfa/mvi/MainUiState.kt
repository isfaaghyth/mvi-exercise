package app.isfa.mvi

import app.isfa.mvi.component.category.CategoryUiState
import app.isfa.mvi.component.product.ProductUiState

data class MainUiState(
    val productUiState: ProductUiState,
    val categoryUiState: CategoryUiState
) {

    companion object {
        val Empty get() = MainUiState(
            productUiState = ProductUiState.Default,
            categoryUiState = CategoryUiState.Default
        )
    }
}
