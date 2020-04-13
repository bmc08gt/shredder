package dev.bmcreations.shredder.features.create.usecases

import dev.bmcreations.shredder.core.architecture.ParameterUsecaseWithCallback
import dev.bmcreations.shredder.features.create.repository.BookmarkCreateRepository
import dev.bmcreations.shredder.features.create.view.BookmarkEditData

class LoadBookmarkUsecase(
    private val repository: BookmarkCreateRepository
) : ParameterUsecaseWithCallback<String?, BookmarkEditData>() {
    override fun execute(p0: String?, cb: (BookmarkEditData) -> Unit) {
        repository.loadBookmark(p0, cb)
    }
}
