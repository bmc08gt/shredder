package dev.bmcreations.expiry.features.create.usecases

import dev.bmcreations.expiry.core.architecture.SetUsecase
import dev.bmcreations.expiry.features.create.repository.BookmarkCreateRepository
import java.util.*

class SetTitleUsecase(
    private val repository: BookmarkCreateRepository
) : SetUsecase<String?>() {
    override fun execute(arg: String?) = repository.setTitle(arg)
}
