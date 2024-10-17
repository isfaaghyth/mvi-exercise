package app.isfa.mvi.core

import kotlinx.coroutines.flow.MutableSharedFlow

interface EventHandler {

    val event: MutableSharedFlow<Event>

    fun sendEvent(event: Event)
}

object FeatureEventHandler : EventHandler {

    private val _event = MutableSharedFlow<Event>(replay = 50)
    override val event: MutableSharedFlow<Event> get() = _event

    override fun sendEvent(event: Event) {
        _event.tryEmit(event)
    }
}