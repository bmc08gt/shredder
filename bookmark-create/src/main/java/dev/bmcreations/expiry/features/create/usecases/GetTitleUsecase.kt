package dev.bmcreations.expiry.features.create.usecases

import dev.bmcreations.expiry.core.architecture.GetUsecase
import dev.bmcreations.expiry.features.create.repository.BookmarkCreateRepository

class GetTitleUsecase(
    private val repository: BookmarkCreateRepository
) : GetUsecase<String?>(){
    override fun execute(): String? = repository.getTitle()
}
