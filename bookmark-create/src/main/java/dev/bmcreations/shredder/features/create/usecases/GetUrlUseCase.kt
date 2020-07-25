package dev.bmcreations.shredder.features.create.usecases

import dev.bmcreations.shredder.features.create.repository.BookmarkCreateRepository

class GetUrlUseCase(
    private val repository: BookmarkCreateRepository
) {
    operator fun invoke(): String? = repository.getUrl()
}
