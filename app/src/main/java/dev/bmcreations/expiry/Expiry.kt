package dev.bmcreations.expiry

import android.app.Application
import dev.bmcreations.expiry.bar.BookmarksComponentImpl
import dev.bmcreations.expiry.core.di.ComponentRouter

class Expiry : Application() {

    override fun onCreate() {
        super.onCreate()
        ComponentRouter.init(this)
        ComponentRouter.inject("bookmarks", BookmarksComponentImpl())
    }
}
