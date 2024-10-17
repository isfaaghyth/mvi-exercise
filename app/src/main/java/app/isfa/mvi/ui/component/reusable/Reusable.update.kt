package app.isfa.mvi.ui.component.reusable

import app.isfa.mvi.core.Event
import app.isfa.mvi.core.Update
import app.isfa.mvi.core.UpdateScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

interface ReusableUpdate : Update {

    val uiState: Flow<CombinedFeatureAFeatureBUiState>
}

class ReusableUpdateImpl(
    private val useCase1: List<String>,
    private val useCase2: List<String>,
    private val updateScope: UpdateScope
) : ReusableUpdate, UpdateScope by updateScope {

    private val _uiState = MutableStateFlow(CombinedFeatureAFeatureBUiState.Empty)
    override val uiState: Flow<CombinedFeatureAFeatureBUiState> get() = _uiState

    override fun handleEvent(event: Event) {
        when (event) {
            is ReusableFeatureA.FetchFeatureAList -> {
                _uiState.update { state ->
                    state.copy(
                        featureAUiState = state.featureAUiState.copy(useCase1)
                    )
                }
            }

            is ReusableFeatureB.FetchFeatureBList -> {
                _uiState.update { state ->
                    state.copy(
                        featureBUiState = state.featureBUiState.copy(useCase2)
                    )
                }
            }
        }
    }
}

data class CombinedFeatureAFeatureBUiState(
    val featureAUiState: ReusableFeatureA.FeatureAUiState,
    val featureBUiState: ReusableFeatureB.FeatureBUiState,
) {

    companion object {
        val Empty
            get() = CombinedFeatureAFeatureBUiState(
                featureAUiState = ReusableFeatureA.FeatureAUiState(listOf()),
                featureBUiState = ReusableFeatureB.FeatureBUiState(listOf()),
            )
    }
}

sealed interface ReusableFeatureA {

    data class FeatureAUiState(
        val items: List<String>
    )

    data object FetchFeatureAList : Event
}

sealed interface ReusableFeatureB {

    data class FeatureBUiState(
        val items: List<String>
    )

    data object FetchFeatureBList : Event
}