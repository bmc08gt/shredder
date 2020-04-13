package dev.bmcreations.expiry.features.create.usecases

import dev.bmcreations.expiry.core.architecture.SetUsecase
import dev.bmcreations.expiry.features.create.repository.BookmarkCreateRepository
import java.util.*

class SetExpirationDateUsecase(
    private val repository: BookmarkCreateRepository
) : SetUsecase<Date?>() {
    override fun execute(arg: Date?) = repository.setExpirationDate(arg)
}
