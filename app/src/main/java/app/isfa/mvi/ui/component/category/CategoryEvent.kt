package app.isfa.mvi.ui.component.category

import app.isfa.mvi.core.Event

object CategoryEvent {

    data class CategoryList(val productName: String) : Event
}