package app.isfa.mvi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    val state: StateFlow<MainUiState>
        get() = MutableStateFlow(MainUiState.Empty)
}