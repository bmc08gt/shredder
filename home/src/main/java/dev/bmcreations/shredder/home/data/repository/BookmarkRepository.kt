package dev.bmcreations.shredder.home.data.repository

import dev.bmcreations.shredder.models.Bookmark
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    fun loadBookmarks(): Flow<List<Bookmark>>
    fun loadBookmark(bookmarkId: String?, callback: (Result<Bookmark>) -> Unit)
    fun upsertBookmark(upsert: Bookmark): Boolean
    fun removeBookmark(removal: Bookmark): Boolean
}
