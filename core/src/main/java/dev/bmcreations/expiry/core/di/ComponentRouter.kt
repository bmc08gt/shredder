package dev.bmcreations.expiry.core.di

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment

object ComponentRouter {

    lateinit var core: CoreComponent

    private val components: MutableMap<String, Component<*>> = mutableMapOf()

    fun init(app: Application) {
        core = CoreComponentImpl(app)
    }

    fun inject(tag: String, component: Component<*>) {
        components[tag] = component
    }

    internal fun component(tag: String): Component<*>? = components[tag]
}

fun Activity.component(tag: String) = ComponentRouter.component(tag)
fun Fragment.component(tag: String) = ComponentRouter.component(tag)
