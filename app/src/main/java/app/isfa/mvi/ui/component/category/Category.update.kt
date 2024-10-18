package app.isfa.mvi.ui.component.category

import app.isfa.mvi.base.UiState
import app.isfa.mvi.core.Event
import app.isfa.mvi.core.EventHandler
import app.isfa.mvi.core.Update
import app.isfa.mvi.core.UpdateScope
import app.isfa.mvi.domain.CategoryUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

interface CategoryUpdate : Update {

    val uiState: Flow<CategoryUiState>
}

class CategoryUpdateImpl(
    private val categoryUseCase: CategoryUseCase,
    private val updateScope: UpdateScope,
    private val eventHandler: EventHandler,
) : CategoryUpdate, UpdateScope by updateScope {

    private val _uiState = MutableStateFlow(CategoryUiState.Default)
    override val uiState: Flow<CategoryUiState> get() = _uiState

    override fun handleEvent(event: Event) {
        when(event) {
            is CategoryEvent.CategoryList -> {
                shouldFetchCategoryByProductName(event.productName)
            }
        }
    }

    private fun shouldFetchCategoryByProductName(name: String) {
        _uiState.update {
            it.copy(
                items = categoryUseCase(name),
                state = UiState.Success
            )
        }
    }
}