package dev.bmcreations.shredder

import android.app.Application
import dev.bmcreations.shredder.core.ComponentRouter

class ShredderApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ComponentRouter.init(this) {}
    }
}
