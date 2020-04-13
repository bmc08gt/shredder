package dev.bmcreations.expiry.features.create.usecases

import dev.bmcreations.expiry.core.architecture.SetUsecase
import dev.bmcreations.expiry.features.create.repository.BookmarkCreateRepository

class SetUrlUsecase(
    private val repository: BookmarkCreateRepository
) : SetUsecase<String?>() {
    override fun execute(arg: String?) = repository.setUrl(arg)
}
