package dev.bmcreations.expiry.features.create.usecases

import dev.bmcreations.expiry.core.architecture.Parameter4UsecaseWithCallback
import dev.bmcreations.expiry.core.architecture.UsecaseWithCallback
import dev.bmcreations.expiry.features.create.repository.BookmarkCreateRepository
import dev.bmcreations.expiry.models.Bookmark
import dev.bmcreations.expiry.models.Group
import java.util.*

class CreateBookmarkUsecase(
    private val repository: BookmarkCreateRepository
) : UsecaseWithCallback<Bookmark?>(){
    override fun execute(cb: (Bookmark?) -> Unit) {
        repository.createBookmark(cb)
    }
}
