package app.isfa.mvi.ui.component.product

import app.isfa.mvi.core.Event

data object ProductList : Event
data class ProductClicked(val name: String) : Event