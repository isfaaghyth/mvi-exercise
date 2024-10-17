package app.isfa.mvi.ui

import app.isfa.mvi.ui.component.category.CategoryUiState
import app.isfa.mvi.ui.component.product.ProductUiState
import app.isfa.mvi.ui.component.reusable.CombinedFeatureAFeatureBUiState

data class MainUiState(
    val productUiState: ProductUiState,
    val categoryUiState: CategoryUiState,
    val reusableUiState: CombinedFeatureAFeatureBUiState
) {

    companion object {
        val Empty get() = MainUiState(
            productUiState = ProductUiState.Default,
            categoryUiState = CategoryUiState.Default,
            reusableUiState = CombinedFeatureAFeatureBUiState.Empty
        )
    }
}
