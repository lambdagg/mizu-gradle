package wtf.mizu.plugin.extension

import org.gradle.api.provider.Property

open class MizuExtension(
    internal var common: String = "",
    internal var events: String = "",
    internal var eventsImpl: EventsImplementation = EventsImplementation.SYNC
) {
    fun common() = apply { common = "1.0.+" }
    fun common(version: String) = apply { common = version }

    fun events(implementation: EventsImplementation = EventsImplementation.SYNC) = apply {
        events = "1.0.+"
        eventsImpl = implementation
    }
    fun events(implementation: EventsImplementation = EventsImplementation.SYNC, version: String) = apply {
        events = version
        eventsImpl = implementation
    }
}

enum class EventsImplementation(val id: String) {
    SYNC("sync-impl")
}