package dev.bmcreations.expiry

import android.app.Application
import dev.bmcreations.expiry.bar.BookmarksComponent
import dev.bmcreations.expiry.bar.BookmarksComponentImpl
import dev.bmcreations.expiry.core.di.ComponentRouter
import dev.bmcreations.expiry.core.di.component
import dev.bmcreations.expiry.di.WebManifestNetworkComponentImpl
import dev.bmcreations.expiry.features.list.di.BookmarkListComponentImpl

class Expiry : Application() {

    override fun onCreate() {
        super.onCreate()
        ComponentRouter.init(this)
        ComponentRouter.inject("webManifest", WebManifestNetworkComponentImpl())
        ComponentRouter.inject("bookmarks", BookmarksComponentImpl())
        ComponentRouter.inject("bookmarks-list", BookmarkListComponentImpl("bookmarks".component()))
    }
}
