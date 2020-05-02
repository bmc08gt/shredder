package dev.bmcreations.shredder.bar

import dev.bmcreations.shredder.bar.bookmarks.BookmarksDao
import dev.bmcreations.shredder.bar.bookmarks.InMemoryBookmarksDao
import dev.bmcreations.shredder.bar.bookmarks.room.RoomBookmarksDao
import dev.bmcreations.shredder.bar.db.BookmarksDatabase
import dev.bmcreations.shredder.bar.groups.GroupsDao
import dev.bmcreations.shredder.bar.groups.InMemoryGroupsDao
import dev.bmcreations.shredder.bar.groups.room.RoomGroupDao
import dev.bmcreations.shredder.core.di.Component
import dev.bmcreations.shredder.core.di.CoreComponent

interface BookmarksComponent: Component {
    val bar: BookmarksBar
}

class BookmarksComponentImpl(core: CoreComponent) : BookmarksComponent {

    private enum class DatabaseType { IN_MEMORY, ROOM }

    private val dbType = DatabaseType.ROOM

    private val bookmarkDao: BookmarksDao = when(dbType) {
        DatabaseType.IN_MEMORY -> InMemoryBookmarksDao()
        DatabaseType.ROOM -> RoomBookmarksDao(BookmarksDatabase.create(core.app))
    }

    private val groupsDao: GroupsDao = when(dbType) {
        DatabaseType.IN_MEMORY -> InMemoryGroupsDao()
        DatabaseType.ROOM -> RoomGroupDao(BookmarksDatabase.create(core.app))
    }

    override val bar: BookmarksBar = BookmarksBar(bookmarkDao, groupsDao)
}



