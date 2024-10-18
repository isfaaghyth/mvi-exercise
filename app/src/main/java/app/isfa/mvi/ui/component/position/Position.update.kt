package app.isfa.mvi.ui.component.position

import app.isfa.mvi.core.Event
import app.isfa.mvi.core.Update
import app.isfa.mvi.core.UpdateScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

interface PositionUpdate : Update {

    val uiState: Flow<PositionUiState>
}

class PositionUpdateImpl(updateScope: UpdateScope) : PositionUpdate, UpdateScope by updateScope {

    private val _uiState = MutableStateFlow(PositionUiState())
    override val uiState: Flow<PositionUiState> get() = _uiState

    override fun handleEvent(event: Event) {
        if (event is PositionEvent.GetPositionList) {
            _uiState.update {
                it.copy(
                    components = listOf(
                        "product_list",
                        "category_list",
                        "category_title",
                    )
                )
            }
        }
    }
}