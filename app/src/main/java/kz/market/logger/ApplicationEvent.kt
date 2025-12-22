package kz.market.logger

sealed interface ApplicationEvent {
    val name: String
    val params: Map<String, String>

    data class Screen(
        val screen: String,
        val args: Map<String, String> = emptyMap()
    ) : ApplicationEvent {
        override val name: String
            get() = "screen"
        override val params: Map<String, String>
            get() = mapOf("screen" to screen) + args
    }

    data class Action(
        val action: String,
        val metadata: Map<String, String> = emptyMap()
    ) : ApplicationEvent {
        override val name: String
            get() = "action"
        override val params: Map<String, String>
            get() = mapOf("action" to action) + metadata
    }

    data class State(
        val state: String,
        val metadata: Map<String, String> = emptyMap()
    ) : ApplicationEvent {
        override val name: String
            get() = "state"
        override val params: Map<String, String>
            get() = mapOf("state" to state) + metadata
    }

    data class Error(
        val code: String,
        val metadata: Map<String, String> = emptyMap()
    ) : ApplicationEvent {
        override val name: String
            get() = "error"
        override val params: Map<String, String>
            get() = mapOf("code" to code) + metadata
    }
}