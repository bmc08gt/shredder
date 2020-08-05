package dev.bmcreations.shredder.bookmarks.usecases

import dev.bmcreations.shredder.bookmarks.dao.BookmarksDao
import dev.bmcreations.shredder.models.Bookmark

class GetBookmarkByUrl(
    private val dao: BookmarksDao
) {
    operator fun invoke(url: String?, callback: (Result<Bookmark>) -> Unit) {
        dao.findByUrl(url.orEmpty(), callback)
    }
}
