@file:Suppress("unused")

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
    fun common(commonVersion: String = BASE_VERSION) =
        this.apply { this.common = commonVersion }

    fun events(
        eventsImplementation: EventsImplementation = EventsImplementation.SYNC,
        eventsVersion: String = BASE_VERSION
    ) = this.apply {
        this.eventsImpl = eventsImplementation
        this.events = eventsVersion
    }

    fun animations(animationsVersion: String = BASE_VERSION) =
        this.apply { this.animations = animationsVersion }

    fun settings(settingsVersion: String = BASE_VERSION) =
        this.apply { this.settings = settingsVersion }

    fun clientApi(clientApiVersion: String = BASE_VERSION) =
        this.apply { this.clientApi = clientApiVersion }

    fun loader(loaderVersion: String = BASE_VERSION) =
        this.apply { this.loader = loaderVersion }
}

enum class EventsImplementation(val id: String) {
    SYNC("sync-impl")
}
