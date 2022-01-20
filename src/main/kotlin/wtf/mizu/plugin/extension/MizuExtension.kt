package wtf.mizu.plugin.extension

open class MizuExtension(
    internal var common: String = "",
    internal var events: String = "",
    internal var eventsImpl: EventsImplementation = EventsImplementation.SYNC,
    internal var animations: String = "",
    internal var settings: String = ""
) {
    fun common(version: String = "1.0.+") = apply { common = version }

    fun events(
        implementation: EventsImplementation = EventsImplementation.SYNC,
        version: String = "1.0.+"
    ) = apply {
        events = version
        eventsImpl = implementation
    }

    fun animations(version: String = "1.0.+") = apply { animations = version }

    fun settings(version: String = "1.0.+") = apply { settings = version }
}

enum class EventsImplementation(val id: String) {
    SYNC("sync-impl")
}