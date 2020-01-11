package dev.bmcreations.expiry.features.create.usecases

import dev.bmcreations.expiry.core.architecture.ParameterUsecaseWithCallback
import dev.bmcreations.expiry.features.create.repository.BookmarkCreateRepository
import dev.bmcreations.expiry.models.Bookmark

class LoadBookmarkUsecase(
    private val repository: BookmarkCreateRepository
) : ParameterUsecaseWithCallback<String?, Bookmark?>() {
    override fun execute(p0: String?, cb: (Bookmark?) -> Unit) {
        repository.loadBookmark(p0, cb)
    }
}
