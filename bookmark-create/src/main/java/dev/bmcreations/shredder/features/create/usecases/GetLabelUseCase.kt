package dev.bmcreations.shredder.features.create.usecases

import dev.bmcreations.shredder.features.create.repository.BookmarkCreateRepository

class GetLabelUseCase(
    private val repository: BookmarkCreateRepository
) {
    operator fun invoke(): String? = repository.getLabel()
}
