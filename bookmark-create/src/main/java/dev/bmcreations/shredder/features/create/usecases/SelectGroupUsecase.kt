package dev.bmcreations.shredder.features.create.usecases

import dev.bmcreations.shredder.core.architecture.SetUsecase
import dev.bmcreations.shredder.features.create.repository.BookmarkCreateRepository
import dev.bmcreations.shredder.models.Group

class SelectGroupUsecase(
    private val repository: BookmarkCreateRepository
) : SetUsecase<Group>() {
    override fun execute(arg: Group) = repository.selectGroup(arg)
}
