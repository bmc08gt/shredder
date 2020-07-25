package dev.bmcreations.shredder.features.create.usecases

import dev.bmcreations.shredder.features.create.repository.BookmarkCreateRepository
import dev.bmcreations.shredder.models.Bookmark

class CreateBookmarkUseCase(
    private val repository: BookmarkCreateRepository
) {
    operator fun invoke(cb: (Bookmark?) -> Unit) {
        repository.createBookmark(cb)
    }
}
