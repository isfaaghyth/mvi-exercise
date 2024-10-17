package app.isfa.mvi.ui.component.product

import app.isfa.mvi.base.UiState
import app.isfa.mvi.core.Event
import app.isfa.mvi.core.EventHandler
import app.isfa.mvi.core.Update
import app.isfa.mvi.core.UpdateScope
import app.isfa.mvi.domain.ProductUseCase
import app.isfa.mvi.ui.component.category.CategoryList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

interface ProductUpdate : Update {

    val uiState: Flow<ProductUiState>
}

class ProductUpdateImpl(
    private val productUseCase: ProductUseCase,
    private val updateScope: UpdateScope,
    private val eventHandler: EventHandler
) : ProductUpdate, UpdateScope by updateScope {

    private val _uiState = MutableStateFlow(ProductUiState.Default)
    override val uiState: Flow<ProductUiState> get() = _uiState

    override fun handleEvent(event: Event) {
        when(event) {
            is ProductList -> shouldFetchProductList()
            is ProductClicked -> {
                eventHandler.sendEvent(CategoryList(event.name))
            }
        }
    }

    private fun shouldFetchProductList() {
        _uiState.update {
            it.copy(
                items = productUseCase(),
                state = UiState.Success
            )
        }
    }
}