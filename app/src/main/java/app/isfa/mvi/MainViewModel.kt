package app.isfa.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.isfa.mvi.base.UiState
import app.isfa.mvi.component.category.CategoryUiState
import app.isfa.mvi.component.product.ProductUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val productUseCase: ProductUseCase = ProductUseCase(),
    private val categoryUseCase: CategoryUseCase = CategoryUseCaseImpl(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private var _event = MutableSharedFlow<MainEvent>(replay = 50)

    private var _effects = Channel<MainEffects?>()
    val effects get() = _effects.receiveAsFlow()

    private val _productUiState = MutableStateFlow(
        ProductUiState(
            items = productUseCase(),
            state = UiState.Success
        )
    )

    private val _categoryUiState = MutableStateFlow(CategoryUiState.Default)

    val state: StateFlow<MainUiState> =
        combine(_productUiState, _categoryUiState) { productUiState, categoryUiState ->
            MainUiState(
                productUiState = productUiState,
                categoryUiState = categoryUiState
            )
        }.flowOn(dispatcher)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = MainUiState.Empty
            )

    init {
        viewModelScope.launch {
            _event
                .distinctUntilChanged()
                .collect(::observeEvent)
        }
    }

    fun sendEvent(event: MainEvent) {
        _event.tryEmit(event)
    }

    private fun observeEvent(event: MainEvent) {
        when (event) {
            is ProductItemClicked -> shouldFetchProductCategory(event.name)
        }
    }

    private fun sendEffect(effects: MainEffects) {
        viewModelScope.launch {
            _effects.send(effects)
        }
    }

    private fun shouldFetchProductCategory(name: String) {
        try {
            val response = categoryUseCase(name)

            if (response.isNotEmpty()) {
                _categoryUiState.update {
                    it.copy(
                        items = response,
                        state = UiState.Success
                    )
                }
            } else {
                _categoryUiState.update {
                    it.copy(
                        items = listOf(),
                        state = UiState.Fail(Throwable("simulate empty list"))
                    )
                }
            }
        } catch (e: Exception) {
            _categoryUiState.update { it.copy(items = listOf()) }
            sendEffect(ShowNetworkError(e.message.toString()))
        }
    }
}

class ProductUseCase {

    operator fun invoke(): List<Pair<Int, String>> {
        return listOf(
            Pair(1, "PLN"),
            Pair(2, "Pulsa"),
            Pair(3, "PDAM"),
            Pair(99, "Foobar - Network Error"),
        )
    }
}

interface CategoryUseCase {

    operator fun invoke(name: String): List<String>
}

class CategoryUseCaseImpl : CategoryUseCase {

    private val response = mapOf(
        "PLN" to listOf("Jakarta", "Makassar", "Bali"),
        "Pulsa" to listOf("Telkomsel", "XL", "Flexi"),
        "PDAM" to listOf(),
    )

    override operator fun invoke(name: String) =
        response[name] ?: error("Aduh, gagal nih.")
}