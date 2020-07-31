package dev.bmcreations.shredder

import android.app.Application
import dev.bmcreations.shredder.core.di.AppComponent
import dev.bmcreations.shredder.core.di.components
import dev.bmcreations.shredder.core.di.ComponentRouter
import dev.bmcreations.shredder.home.di.HomeComponentImpl

class ShredderApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ComponentRouter.init(this) {
            val app by components<AppComponent>()
            inject(HomeComponentImpl(app))
        }
    }
}
