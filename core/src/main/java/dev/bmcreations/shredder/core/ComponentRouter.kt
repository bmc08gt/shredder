package dev.bmcreations.shredder.core

import android.app.Application

interface Component

object ComponentRouter {

    val components: MutableMap<String, Component> = mutableMapOf()

    fun init(app: Application, block: Initializer.() -> Unit) {
        Initializer().apply {
            this.inject(Components.APP, AppComponentImpl(app))
            block.invoke(this)
        }
    }

    class Initializer {
        fun inject(tag: String, component: Component) {
            components[tag] = component
        }
    }
}

object Components {
    const val APP = "app"
}
