package dev.bmcreations.shredder.features.create.usecases

import dev.bmcreations.shredder.core.architecture.UsecaseWithCallback
import dev.bmcreations.shredder.features.create.repository.BookmarkCreateRepository
import dev.bmcreations.shredder.models.Bookmark

class CreateBookmarkUsecase(
    private val repository: BookmarkCreateRepository
) : UsecaseWithCallback<Bookmark?>(){
    override fun execute(cb: (Bookmark?) -> Unit) {
        repository.createBookmark(cb)
    }
}
