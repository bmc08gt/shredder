package dev.bmcreations.shredder

import android.app.Application
import dev.bmcreations.shredder.di.AppContainer
import dev.bmcreations.shredder.di.AppContainerImpl

class ShredderApplication : Application() {

    // AppContainer instance used by the rest of classes to obtain dependencies
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl(this)
    }
}