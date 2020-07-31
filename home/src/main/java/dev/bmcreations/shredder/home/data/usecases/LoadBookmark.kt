package dev.bmcreations.shredder.home.data.usecases

import dev.bmcreations.shredder.home.data.repository.BookmarkRepository
import dev.bmcreations.shredder.home.ui.edit.EditModel
import dev.bmcreations.shredder.models.Bookmark

class LoadBookmark(
    private val repository: BookmarkRepository
) {
    operator fun invoke(bookmarkId: String?, callback: (Result<Bookmark>) -> Unit) = repository.loadBookmark(bookmarkId, callback)
}
