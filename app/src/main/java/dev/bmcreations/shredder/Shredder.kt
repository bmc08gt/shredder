package dev.bmcreations.shredder

import android.app.Application
import dev.bmcreations.shredder.bar.BookmarksComponentImpl
import dev.bmcreations.shredder.core.di.*
import dev.bmcreations.shredder.features.create.di.BookmarkCreateComponentImpl
import dev.bmcreations.shredder.features.list.di.BookmarkListComponentImpl
import dev.bmcreations.shredder.login.di.LoginComponentImpl

class Shredder : Application() {

    override fun onCreate() {
        super.onCreate()
        ComponentRouter.init(this) {
            inject(
                Components.LOGIN,
                LoginComponentImpl(Components.CORE.component())
            )
            inject(Components.BOOKMARKS, BookmarksComponentImpl())
            inject(
                Components.BOOKMARKS_LIST,
                BookmarkListComponentImpl(
                    Components.CORE.component(),
                    Components.BOOKMARKS.component()
                )
            )
            inject(
                Components.BOOKMARKS_CREATE,
                BookmarkCreateComponentImpl(
                    Components.CORE.component(),
                    Components.BOOKMARKS.component()
                )
            )
        }
    }
}
