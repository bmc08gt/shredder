package dev.bmcreations.shredder.home.di

import dev.bmcreations.shredder.bookmarks.Library
import dev.bmcreations.shredder.bookmarks.dao.InMemoryBookmarksDao
import dev.bmcreations.shredder.bookmarks.usecases.*
import dev.bmcreations.shredder.core.di.AppComponent
import dev.bmcreations.shredder.core.di.Component

/**
 * Dependency Injection container at the application level.
 */
interface HomeComponent : Component {
    val bookmarkLibrary: Library
}

class HomeComponentImpl(appComponent: AppComponent) : HomeComponent {
    companion object {
        enum class IMPLEMENTATION  { InMemory }
    }

    private val implementation = IMPLEMENTATION.InMemory


    private val daoImpl = when (implementation) {
        IMPLEMENTATION.InMemory -> InMemoryBookmarksDao()
    }

    override val bookmarkLibrary: Library = Library(
        getBookmarks = GetBookmarks(daoImpl),
        getBookmarkById = GetBookmarkById(daoImpl),
        getBookmarkByLabel = GetBookmarkByLabel(daoImpl),
        getBookmarkByUrl = GetBookmarkByUrl(daoImpl),
        upsertBookmark = UpsertBookmark(daoImpl),
        deleteBookmark = DeleteBookmark(daoImpl),
        clearBookmarks = ClearBookmarks(daoImpl)
    )
}
