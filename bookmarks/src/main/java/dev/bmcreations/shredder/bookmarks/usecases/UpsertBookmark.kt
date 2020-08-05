package dev.bmcreations.shredder.bookmarks.usecases

import dev.bmcreations.shredder.bookmarks.dao.BookmarksDao
import dev.bmcreations.shredder.models.Bookmark

class UpsertBookmark(
    private val dao: BookmarksDao
) {
    suspend operator fun invoke(upsert: Bookmark) = dao.upsert(upsert)
}
