package dev.bmcreations.expiry.features.create.usecases

import androidx.lifecycle.LiveData
import dev.bmcreations.expiry.core.architecture.GetUsecase
import dev.bmcreations.expiry.core.architecture.SuspendingGetUsecase
import dev.bmcreations.expiry.features.create.repository.BookmarkCreateRepository
import dev.bmcreations.expiry.models.Group

class GetGroupsLiveDataUsecase(
    private val repository: BookmarkCreateRepository
) : SuspendingGetUsecase<LiveData<List<Group>>>() {
    override suspend fun execute(): LiveData<List<Group>> = repository.groups()
}
