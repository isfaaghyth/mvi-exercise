package app.isfa.mvi.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

interface Event

interface Update {

    fun handleEvent(event: Event)
}

interface UpdateScope : AutoCloseable {

    val scope: CoroutineScope

    fun shouldUseViewModelScope(scope: CoroutineScope)
}

class MainUpdateScope : UpdateScope {

    private var viewModelScope: CoroutineScope? = null

    override val scope: CoroutineScope
        get() = viewModelScope ?: object : CoroutineScope {

            override val coroutineContext: CoroutineContext
                get() = try {
                    Dispatchers.Main.immediate
                } catch (_: NotImplementedError) {
                    EmptyCoroutineContext
                } catch (_: IllegalStateException) {
                    EmptyCoroutineContext
                } + SupervisorJob()
        }

    override fun shouldUseViewModelScope(scope: CoroutineScope) {
        viewModelScope = scope
    }

    override fun close() {
        scope.coroutineContext.cancelChildren()
    }
}