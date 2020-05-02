package dev.bmcreations.shredder.features.create.usecases

import androidx.lifecycle.LiveData
import dev.bmcreations.shredder.core.architecture.SuspendingGetUsecase
import dev.bmcreations.shredder.features.create.repository.BookmarkCreateRepository
import dev.bmcreations.shredder.models.Group

class GetGroupsLiveDataUsecase(
    private val repository: BookmarkCreateRepository
) : SuspendingGetUsecase<LiveData<List<Group>>>() {
    override suspend fun execute(): LiveData<List<Group>> = repository.groups()
}
