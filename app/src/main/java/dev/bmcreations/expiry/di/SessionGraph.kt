package dev.bmcreations.expiry.di

import android.content.Context
import dev.bmcreations.expiry.bar.BookmarkBar
import dev.bmcreations.expiry.bar.BookmarksDao
import dev.bmcreations.expiry.bar.InMemoryBookmarksDao
import dev.bmcreations.expiry.preferences.UserPreferences
import dev.bmcreations.expiry.repository.SessionManager

interface SessionGraph {
    val bookmarkBar: BookmarkBar
    val sessionManager: SessionManager
    val userPrefs: UserPreferences
}

class SessionGraphImpl(appContext: Context): SessionGraph {

    private enum class DatabaseType { IN_MEMORY }

    private val dbType = DatabaseType.IN_MEMORY

    private val bookmarkDao: BookmarksDao = when(dbType) {
        DatabaseType.IN_MEMORY -> InMemoryBookmarksDao()
    }

    override val bookmarkBar: BookmarkBar = BookmarkBar(bookmarkDao)
    override val userPrefs: UserPreferences = UserPreferences(appContext)

    override val sessionManager: SessionManager = SessionManager(bookmarkBar, userPrefs)
}
