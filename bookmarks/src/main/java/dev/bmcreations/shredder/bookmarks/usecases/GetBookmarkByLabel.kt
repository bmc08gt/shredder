package dev.bmcreations.shredder.bookmarks.usecases

import dev.bmcreations.shredder.bookmarks.dao.BookmarksDao
import dev.bmcreations.shredder.models.Bookmark

class GetBookmarkByLabel(
    private val dao: BookmarksDao
) {
    operator fun invoke(label: String?, callback: (Result<Bookmark>) -> Unit) {
        dao.findByLabel(label.orEmpty(), callback)
    }
}
