package dev.bmcreations.expiry.features.create.usecases

import dev.bmcreations.expiry.core.architecture.GetUsecase
import dev.bmcreations.expiry.features.create.repository.BookmarkCreateRepository
import java.util.*

class GetExpirationDateUsecase(
    private val repository: BookmarkCreateRepository
) : GetUsecase<Date?>() {
    override fun execute() = repository.getExpirationDate()
}
