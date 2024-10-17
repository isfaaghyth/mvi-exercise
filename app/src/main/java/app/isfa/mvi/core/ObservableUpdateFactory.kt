package app.isfa.mvi.core

import kotlinx.coroutines.CoroutineScope

interface UpdateFactory {
    fun registerUpdates(vararg update: Update)
    fun eventHandlers(event: Event)
}

class ObservableUpdateFactory(private val scope: CoroutineScope) : UpdateFactory {

    private val updates = LoopMutableList(
        invoke = { setupLoop() }
    )

    override fun registerUpdates(vararg update: Update) {
        update.forEach { updates.add(it) }
    }

    override fun eventHandlers(event: Event) =
        updates.forEach { it.handleEvent(event) }

    private fun setupLoop() {
        updates
            .map { it as UpdateScope }
            .map { it.shouldUseViewModelScope(scope) }
    }


}

class LoopMutableList(
    private val invoke: () -> Unit,
    private val default: MutableList<Update> = mutableListOf()
) : MutableList<Update> by default {

    override fun add(element: Update): Boolean {
        return default.add(element).also { invoke() }
    }
}