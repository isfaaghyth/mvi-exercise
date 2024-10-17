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
import app.isfa.mvi.ui.component.reusable.ReusableFeatureA
import app.isfa.mvi.ui.component.reusable.ReusableFeatureB
import app.isfa.mvi.ui.component.reusable.ReusableUpdate
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
    reusableUpdate: ReusableUpdate = FeatureModule.provideReusableUpdate(),
    private val eventHandler: EventHandler = FeatureModule.provideEventHandler()
) : ViewModel() {

    private val factory = ObservableUpdateFactory(
        scope = viewModelScope
    )

    val state: StateFlow<MainUiState> =
        combine(
            productUpdate.uiState,
            categoryUpdate.uiState,
            reusableUpdate.uiState
        ) { productUiState, categoryUiState, reusableUiState ->
            MainUiState(
                productUiState = productUiState,
                categoryUiState = categoryUiState,
                reusableUiState = reusableUiState
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
            categoryUpdate,
            reusableUpdate
        )

        viewModelScope.launch {
            eventHandler.event
                .distinctUntilChanged()
                .collect { factory.eventHandlers(it) }
        }

        // init
        sendEvent(ProductList)

        // sample
        sendEvent(ReusableFeatureA.FetchFeatureAList)
        sendEvent(ReusableFeatureB.FetchFeatureBList)
    }

    fun sendEvent(event: Event) {
        eventHandler.sendEvent(event)
    }
}
