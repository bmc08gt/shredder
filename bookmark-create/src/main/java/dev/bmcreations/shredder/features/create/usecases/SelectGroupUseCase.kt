package dev.bmcreations.shredder.features.create.usecases

import dev.bmcreations.shredder.features.create.repository.BookmarkCreateRepository
import dev.bmcreations.shredder.models.Group

class SelectGroupUseCase(
    private val repository: BookmarkCreateRepository
)  {
    operator fun invoke(group: Group) = repository.selectGroup(group)
}
