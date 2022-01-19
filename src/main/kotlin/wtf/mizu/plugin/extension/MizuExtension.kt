package wtf.mizu.plugin.extension

import org.gradle.api.provider.Property

open class MizuExtension(
    internal var common: String = "",
    internal var events: String = "",
    internal var eventsImpl: EventsImplementation = EventsImplementation.SYNC,
    internal var animations: String = "",
    internal var settings: String = ""
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

    fun animations() = apply { animations = "1.0.+" }
    fun animations(version: String) = apply { animations = version }

    fun settings() = apply { settings = "1.0.+" }
    fun settings(version: String) = apply { settings = version }
}

enum class EventsImplementation(val id: String) {
    SYNC("sync-impl")
}