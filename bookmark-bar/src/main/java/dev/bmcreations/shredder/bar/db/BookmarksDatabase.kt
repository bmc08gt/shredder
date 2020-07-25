package dev.bmcreations.shredder.bar.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.bmcreations.shredder.bar.bookmarks.room.RoomItemInBookmarkDao
import dev.bmcreations.shredder.bar.groups.room.RoomItemInGroupDao
import dev.bmcreations.shredder.models.*

@Database(entities = [Bookmark::class, Group::class], version = 1, exportSchema = true)
@TypeConverters(BookmarkConverter::class, GroupConverter::class, SiteConverter::class, DateConverter::class)
abstract class BookmarksDatabase: RoomDatabase() {

    abstract fun itemsInBookmarksDao(): RoomItemInBookmarkDao
    abstract fun itemsInGroupsDao(): RoomItemInGroupDao

    companion object {
        fun create(context: Context): BookmarksDatabase {
            synchronized(BookmarksDatabase::class) {
                return Room.databaseBuilder(
                    context.applicationContext,
                    BookmarksDatabase::class.java, "shredder__bookmarks_database"
                ).apply {
                    // fallback to destructive migration on downgrades
                    // e.g when DB change is up in a PR and you awaiting review
                    // while working on other tasks
                    if (BuildConfig.DEBUG) {
                        fallbackToDestructiveMigrationOnDowngrade()
                    }
                }.build()
            }
        }
    }
}
