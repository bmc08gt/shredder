package dev.bmcreations.shredder.features.create.usecases

import dev.bmcreations.shredder.core.architecture.GetUsecase
import dev.bmcreations.shredder.features.create.repository.BookmarkCreateRepository
import dev.bmcreations.shredder.models.Group

class GetSelectedGroupUseCase(
    private val repository: BookmarkCreateRepository
) {
    operator fun invoke(): Group? = repository.selectedGroup()
}
