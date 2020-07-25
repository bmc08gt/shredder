package dev.bmcreations.shredder.features.create.usecases

import dev.bmcreations.shredder.features.create.repository.BookmarkCreateRepository

class SetLabelUseCase(
    private val repository: BookmarkCreateRepository
)  {
    operator fun invoke(label: String?) = repository.setLabel(label)
}
