package dev.bmcreations.shredder

import android.app.Application
import dev.bmcreations.shredder.bar.BookmarksComponentImpl
import dev.bmcreations.shredder.core.di.*
import dev.bmcreations.shredder.di.WebManifestNetworkComponentImpl
import dev.bmcreations.shredder.features.create.di.BookmarkCreateComponentImpl
import dev.bmcreations.shredder.features.list.di.BookmarkListComponentImpl

class Shredder : Application() {

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
