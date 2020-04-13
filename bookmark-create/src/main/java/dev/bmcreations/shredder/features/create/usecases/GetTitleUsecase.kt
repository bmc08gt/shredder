package dev.bmcreations.shredder.features.create.usecases

import dev.bmcreations.shredder.core.architecture.GetUsecase
import dev.bmcreations.shredder.features.create.repository.BookmarkCreateRepository

class GetTitleUsecase(
    private val repository: BookmarkCreateRepository
) : GetUsecase<String?>(){
    override fun execute(): String? = repository.getTitle()
}
