package dev.bmcreations.shredder.features.create.usecases

import dev.bmcreations.shredder.core.architecture.GetUsecase
import dev.bmcreations.shredder.features.create.repository.BookmarkCreateRepository
import dev.bmcreations.shredder.models.Group

class GetSelectedGroupUsecase(
    private val repository: BookmarkCreateRepository
) : GetUsecase<Group?>(){
    override fun execute(): Group? = repository.selectedGroup()
}
