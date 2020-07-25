package dev.bmcreations.shredder.features.create.usecases

import androidx.lifecycle.LiveData
import dev.bmcreations.shredder.features.create.repository.BookmarkCreateRepository
import dev.bmcreations.shredder.models.Group

class GetGroupsLiveDataUseCase(
    private val repository: BookmarkCreateRepository
)  {
    suspend operator fun invoke(): LiveData<List<Group>> = repository.groups()
}
