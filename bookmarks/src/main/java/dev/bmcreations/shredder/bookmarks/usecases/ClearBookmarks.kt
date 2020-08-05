package dev.bmcreations.shredder.bookmarks.usecases

import dev.bmcreations.shredder.bookmarks.dao.BookmarksDao
import dev.bmcreations.shredder.models.Bookmark

class ClearBookmarks(
    private val dao: BookmarksDao
) {
    suspend operator fun invoke() = dao.empty()
}
