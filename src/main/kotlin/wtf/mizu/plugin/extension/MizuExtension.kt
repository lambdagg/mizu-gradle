@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package wtf.mizu.plugin.extension

const val BASE_VERSION = "1.0.+"

open class MizuExtension(
    internal var common: String = "",
    internal var events: String = "",
    internal var eventsImpl: EventsImplementation = EventsImplementation.SYNC,
    internal var animations: String = "",
    internal var settings: String = "",
    internal var clientApi: String = "",
    internal var loader: String = ""
) {
    fun common(version: String = BASE_VERSION) =
        this.apply { this.common = version }

    fun events(
        implementation: EventsImplementation = EventsImplementation.SYNC,
        version: String = BASE_VERSION
    ) = this.apply {
        this.eventsImpl = implementation
        this.events = version
    }

    fun animations(version: String = BASE_VERSION) =
        this.apply { this.animations = version }

    fun settings(version: String = BASE_VERSION) =
        this.apply { this.settings = version }

    fun clientApi(version: String = BASE_VERSION) =
        this.apply { this.clientApi = version }

    fun loader(version: String = BASE_VERSION) =
        this.apply { this.loader = version }
}

enum class EventsImplementation(val id: String) {
    SYNC("sync-impl")
}
