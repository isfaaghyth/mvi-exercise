package app.isfa.mvi.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.isfa.mvi.core.Event
import app.isfa.mvi.core.EventHandler
import app.isfa.mvi.core.ObservableUpdateFactory
import app.isfa.mvi.di.FeatureModule
import app.isfa.mvi.ui.component.category.CategoryUpdate
import app.isfa.mvi.ui.component.position.PositionEvent
import app.isfa.mvi.ui.component.position.PositionUpdate
import app.isfa.mvi.ui.component.product.ProductEvent
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
    positionUpdate: PositionUpdate = FeatureModule.providePositionUpdate(),
    categoryUpdate: CategoryUpdate = FeatureModule.provideCategoryUpdate(),
    productUpdate: ProductUpdate = FeatureModule.provideProductUpdate(),
    private val eventHandler: EventHandler = FeatureModule.provideEventHandler()
) : ViewModel() {

    private val factory = ObservableUpdateFactory(
        scope = viewModelScope
    )

    val state: StateFlow<MainUiState> =
        combine(
            positionUpdate.uiState,
            productUpdate.uiState,
            categoryUpdate.uiState,
        ) { positionUpdate, productUiState, categoryUiState ->
            MainUiState(
                positionUiState = positionUpdate,
                productUiState = productUiState,
                categoryUiState = categoryUiState,
            )
        }.flowOn(Dispatchers.IO)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = MainUiState.Empty
            )

    init {
        factory.registerUpdates(
            productUpdate,
            categoryUpdate,
            positionUpdate
        )

        viewModelScope.launch {
            eventHandler.event
                .distinctUntilChanged()
                .collect { factory.eventHandlers(it) }
        }

        // init
        sendEvent(ProductEvent.ProductList)
        sendEvent(PositionEvent.GetPositionList)
    }

    fun sendEvent(event: Event) {
        eventHandler.sendEvent(event)
    }
}
