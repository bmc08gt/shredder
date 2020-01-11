package dev.bmcreations.expiry.features.create.usecases

import dev.bmcreations.expiry.core.architecture.GetUsecase
import dev.bmcreations.expiry.features.create.repository.BookmarkCreateRepository
import dev.bmcreations.expiry.models.Group

class GetSelectedGroupUsecase(
    private val repository: BookmarkCreateRepository
) : GetUsecase<Group?>(){
    override fun execute(): Group? = repository.selectedGroup()
}
