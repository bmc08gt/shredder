package dev.bmcreations.shredder.features.create.usecases

import dev.bmcreations.shredder.core.architecture.GetUsecase
import dev.bmcreations.shredder.features.create.repository.BookmarkCreateRepository
import java.util.*

class GetExpirationDateUsecase(
    private val repository: BookmarkCreateRepository
) : GetUsecase<Date?>() {
    override fun execute() = repository.getExpirationDate()
}
