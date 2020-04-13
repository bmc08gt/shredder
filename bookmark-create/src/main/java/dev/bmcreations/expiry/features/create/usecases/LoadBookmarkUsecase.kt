package dev.bmcreations.expiry.features.create.usecases

import dev.bmcreations.expiry.core.architecture.ParameterUsecaseWithCallback
import dev.bmcreations.expiry.features.create.repository.BookmarkCreateRepository
import dev.bmcreations.expiry.features.create.view.BookmarkEditData
import dev.bmcreations.expiry.models.Bookmark

class LoadBookmarkUsecase(
    private val repository: BookmarkCreateRepository
) : ParameterUsecaseWithCallback<String?, BookmarkEditData>() {
    override fun execute(p0: String?, cb: (BookmarkEditData) -> Unit) {
        repository.loadBookmark(p0, cb)
    }
}
