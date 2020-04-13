package dev.bmcreations.shredder.features.create.usecases

import dev.bmcreations.shredder.core.architecture.SetUsecase
import dev.bmcreations.shredder.features.create.repository.BookmarkCreateRepository
import java.util.*

class SetExpirationDateUsecase(
    private val repository: BookmarkCreateRepository
) : SetUsecase<Date?>() {
    override fun execute(arg: Date?) = repository.setExpirationDate(arg)
}
