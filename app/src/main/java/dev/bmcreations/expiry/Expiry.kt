package dev.bmcreations.expiry

import android.app.Application
import dev.bmcreations.expiry.bar.BookmarksComponentImpl
import dev.bmcreations.expiry.core.di.*
import dev.bmcreations.expiry.di.WebManifestNetworkComponentImpl
import dev.bmcreations.expiry.features.create.di.BookmarkCreateComponentImpl
import dev.bmcreations.expiry.features.list.di.BookmarkListComponentImpl

class Expiry : Application() {

    override fun onCreate() {
        super.onCreate()
        ComponentRouter.init(this)
        ComponentRouter.inject(Components.CORE, CoreComponentImpl(this))
        ComponentRouter.inject(
            Components.WEB_MANIFEST,
            WebManifestNetworkComponentImpl()
        )
        ComponentRouter.inject(
            Components.BOOKMARKS,
            BookmarksComponentImpl()
        )
        ComponentRouter.inject(
            Components.BOOKMARKS_LIST,
            BookmarkListComponentImpl(Components.BOOKMARKS.component())
        )
        ComponentRouter.inject(
            Components.BOOKMARKS_CREATE,
            BookmarkCreateComponentImpl(Components.CORE.component(),
                Components.BOOKMARKS.component()
            )
        )
    }
}
