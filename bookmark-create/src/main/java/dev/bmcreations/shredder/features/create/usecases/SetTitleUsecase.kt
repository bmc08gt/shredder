package dev.bmcreations.shredder.features.create.usecases

import dev.bmcreations.shredder.core.architecture.SetUsecase
import dev.bmcreations.shredder.features.create.repository.BookmarkCreateRepository

class SetTitleUsecase(
    private val repository: BookmarkCreateRepository
) : SetUsecase<String?>() {
    override fun execute(arg: String?) = repository.setTitle(arg)
}
