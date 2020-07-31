package dev.bmcreations.shredder.core.di

import android.app.Application

interface Component

object ComponentRouter {

    val components: MutableMap<Class<Component>, Component> = mutableMapOf()

    fun init(app: Application, block: Initializer.() -> Unit) {
        Initializer().apply {
            this.inject(AppComponentImpl(app))
            block.invoke(this)
        }
    }

    class Initializer {
        fun inject(component: Component) {
            with(component) {
                components[this.javaClass] = component
            }
        }
    }
}
