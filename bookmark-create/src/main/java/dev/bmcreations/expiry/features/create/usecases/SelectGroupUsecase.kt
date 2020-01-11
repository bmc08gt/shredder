package dev.bmcreations.expiry.features.create.usecases

import dev.bmcreations.expiry.core.architecture.SetUsecase
import dev.bmcreations.expiry.features.create.repository.BookmarkCreateRepository
import dev.bmcreations.expiry.models.Group

class SelectGroupUsecase(
    private val repository: BookmarkCreateRepository
) : SetUsecase<Group>() {
    override fun execute(arg: Group) = repository.selectGroup(arg)
}
