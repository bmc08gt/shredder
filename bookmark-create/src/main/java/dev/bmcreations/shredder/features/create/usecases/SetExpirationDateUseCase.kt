package dev.bmcreations.shredder.features.create.usecases

import dev.bmcreations.shredder.features.create.repository.BookmarkCreateRepository
import java.util.*

class SetExpirationDateUseCase(
    private val repository: BookmarkCreateRepository
) {
    operator fun invoke(date: Date?) = repository.setExpirationDate(date)
}
