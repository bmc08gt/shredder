package dev.bmcreations.expiry.bar

import dev.bmcreations.expiry.core.di.Component

interface BookmarksComponent: Component {
    val bar: BookmarksBar
}

class BookmarksComponentImpl : BookmarksComponent {

    private enum class DatabaseType { IN_MEMORY }

    private val dbType = DatabaseType.IN_MEMORY

    private val bookmarkDao: BookmarksDao = when(dbType) {
        DatabaseType.IN_MEMORY -> InMemoryBookmarksDao()
    }

    override val bar: BookmarksBar = BookmarksBar(bookmarkDao)
}



