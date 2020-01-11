package dev.bmcreations.expiry.features.create.usecases

import dev.bmcreations.expiry.core.architecture.Parameter4UsecaseWithCallback
import dev.bmcreations.expiry.features.create.repository.BookmarkCreateRepository
import dev.bmcreations.expiry.models.Bookmark
import dev.bmcreations.expiry.models.Group
import java.util.*

class CreateBookmarkUsecase(
    private val repository: BookmarkCreateRepository
) : Parameter4UsecaseWithCallback<String, String, Date?, Group?, Bookmark?>(){
    override fun execute(p0: String, p1: String, p2: Date?, p3: Group?, cb: (Bookmark?) -> Unit) {
        repository.createBookmark(p0, p1, p2, p3, cb)
    }
}
