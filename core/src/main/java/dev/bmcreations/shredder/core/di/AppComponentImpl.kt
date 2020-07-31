package dev.bmcreations.shredder.core.di

import android.content.Context


/**
 * Dependency Injection container at the application level.
 */
interface AppComponent : Component {
    val appContext: Context
}
class AppComponentImpl(override val appContext: Context) : AppComponent {

}
