package dev.bmcreations.expiry.core.di

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment

interface Component {

}

object ComponentRouter {

    lateinit var core: CoreComponent

    private val components: MutableMap<String, Component> = mutableMapOf()

    fun init(app: Application) {
        core = CoreComponentImpl(app)
    }

    fun inject(tag: String, component: Component) {
        components[tag] = component
    }

    internal fun component(tag: String): Component? = components[tag]
}

fun <T> String.component() = ComponentRouter.component(this) as T

object Components {
    const val WEB_MANIFEST = "webManifest"
    const val BOOKMARKS = "bookmarks"
    const val BOOKMARKS_LIST = "bookmarks-list"
    const val BOOKMARKS_CREATE = "bookmark-create"
}
