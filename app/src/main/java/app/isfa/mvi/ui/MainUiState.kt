package app.isfa.mvi.ui

import app.isfa.mvi.ui.component.category.CategoryUiState
import app.isfa.mvi.ui.component.position.PositionUiState
import app.isfa.mvi.ui.component.product.ProductUiState

data class MainUiState(
    val positionUiState: PositionUiState,
    val productUiState: ProductUiState,
    val categoryUiState: CategoryUiState,
) {

    companion object {
        val Empty get() = MainUiState(
            positionUiState = PositionUiState(listOf()),
            productUiState = ProductUiState.Default,
            categoryUiState = CategoryUiState.Default,
        )
    }
}
