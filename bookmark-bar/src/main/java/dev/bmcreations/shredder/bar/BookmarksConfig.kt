package dev.bmcreations.shredder.bar

import dev.bmcreations.shredder.bar.bookmarks.BookmarksDao
import dev.bmcreations.shredder.bar.bookmarks.InMemoryBookmarksDao
import dev.bmcreations.shredder.bar.groups.GroupsDao
import dev.bmcreations.shredder.bar.groups.InMemoryGroupsDao
import dev.bmcreations.shredder.core.di.Component

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



