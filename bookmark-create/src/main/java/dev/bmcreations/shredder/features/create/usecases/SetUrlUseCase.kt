package dev.bmcreations.shredder.features.create.usecases

import dev.bmcreations.shredder.features.create.repository.BookmarkCreateRepository

class SetUrlUseCase(
    private val repository: BookmarkCreateRepository
)  {
    operator fun invoke(url: String?) = repository.setUrl(url)
}
