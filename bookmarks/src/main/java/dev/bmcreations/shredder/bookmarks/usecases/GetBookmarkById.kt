package dev.bmcreations.shredder.bookmarks.usecases

import dev.bmcreations.shredder.bookmarks.dao.BookmarksDao
import dev.bmcreations.shredder.models.Bookmark

class GetBookmarkById(
    private val dao: BookmarksDao
) {
    operator fun invoke(bookmarkId: String?, callback: (Result<Bookmark>) -> Unit) {
        dao.findById(bookmarkId.orEmpty(), callback)
    }
}
