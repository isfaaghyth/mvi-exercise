package app.isfa.mvi.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.isfa.mvi.core.Event
import app.isfa.mvi.core.EventHandler
import app.isfa.mvi.core.ObservableUpdateFactory
import app.isfa.mvi.di.FeatureModule
import app.isfa.mvi.ui.component.category.CategoryUpdate
import app.isfa.mvi.ui.component.product.ProductList
import app.isfa.mvi.ui.component.product.ProductUpdate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    categoryUpdate: CategoryUpdate = FeatureModule.provideCategoryUpdate(),
    productUpdate: ProductUpdate = FeatureModule.provideProductUpdate(),
    private val eventHandler: EventHandler = FeatureModule.provideEventHandler()
) : ViewModel() {

    private val factory = ObservableUpdateFactory(
        scope = viewModelScope
    )

    val state: StateFlow<MainUiState> =
        combine(
            productUpdate.uiState,
            categoryUpdate.uiState
        ) { productUiState, categoryUiState ->
            MainUiState(
                productUiState = productUiState,
                categoryUiState = categoryUiState
            )
        }.flowOn(Dispatchers.IO)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = MainUiState.Empty
            )

    init {
        // don't forget to register your component here
        factory.registerUpdates(
            productUpdate,
            categoryUpdate
        )

        viewModelScope.launch {
            eventHandler.event
                .distinctUntilChanged()
                .collect { factory.eventHandlers(it) }
        }

        sendEvent(ProductList)
    }

    fun sendEvent(event: Event) {
        eventHandler.sendEvent(event)
    }
}
