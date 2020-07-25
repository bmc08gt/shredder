package dev.bmcreations.shredder.features.create.usecases

import dev.bmcreations.shredder.core.architecture.GetUsecase
import dev.bmcreations.shredder.features.create.repository.BookmarkCreateRepository
import java.util.*

class GetExpirationDateUseCase(
    private val repository: BookmarkCreateRepository
) {
    operator fun invoke(): Date? = repository.getExpirationDate()
}
