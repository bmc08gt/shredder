package dev.bmcreations.expiry.bar

import dev.bmcreations.expiry.bar.bookmarks.BookmarksDao
import dev.bmcreations.expiry.bar.bookmarks.InMemoryBookmarksDao
import dev.bmcreations.expiry.bar.groups.GroupsDao
import dev.bmcreations.expiry.bar.groups.InMemoryGroupsDao
import dev.bmcreations.expiry.core.di.Component

interface BookmarksComponent: Component {
    val bar: BookmarksBar
}

class BookmarksComponentImpl :
    BookmarksComponent {

    private enum class DatabaseType { IN_MEMORY }

    private val dbType = DatabaseType.IN_MEMORY

    private val bookmarkDao: BookmarksDao = when(dbType) {
        DatabaseType.IN_MEMORY -> InMemoryBookmarksDao()
    }

    private val groupsDao: GroupsDao = when(dbType) {
        DatabaseType.IN_MEMORY -> InMemoryGroupsDao()
    }

    override val bar: BookmarksBar = BookmarksBar(bookmarkDao, groupsDao)
}



