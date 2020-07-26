package dev.bmcreations.shredder.di

import android.content.Context

/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {

}
class AppContainerImpl(private val applicationContext: Context) : AppContainer {

}