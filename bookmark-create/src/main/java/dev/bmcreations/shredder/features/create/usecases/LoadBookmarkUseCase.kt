package dev.bmcreations.shredder.features.create.usecases

import dev.bmcreations.shredder.features.create.repository.BookmarkCreateRepository
import dev.bmcreations.shredder.features.create.view.BookmarkEditData

class LoadBookmarkUseCase(
    private val repository: BookmarkCreateRepository
) {
    operator fun invoke(id: String?, cb: (BookmarkEditData) -> Unit) {
        repository.loadBookmark(id, cb)
    }
}
