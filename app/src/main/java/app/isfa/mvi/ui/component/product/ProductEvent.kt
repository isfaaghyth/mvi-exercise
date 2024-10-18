package app.isfa.mvi.ui.component.product

import app.isfa.mvi.core.Event

object ProductEvent {
    data object ProductList : Event
    data class ProductClicked(val name: String) : Event
}