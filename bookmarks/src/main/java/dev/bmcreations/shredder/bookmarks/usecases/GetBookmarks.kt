package dev.bmcreations.shredder.bookmarks.usecases

import dev.bmcreations.shredder.bookmarks.dao.BookmarksDao
import dev.bmcreations.shredder.models.Bookmark

class GetBookmarks(
    private val dao: BookmarksDao
) {
    suspend operator fun invoke(): List<Bookmark> {
        return dao.selectAll().sortedByDescending { it.createdAt }
    }
}
