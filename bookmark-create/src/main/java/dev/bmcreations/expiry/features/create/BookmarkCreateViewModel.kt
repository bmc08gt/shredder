package dev.bmcreations.expiry.features.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bmcreations.expiry.core.architecture.Request
import dev.bmcreations.expiry.core.architecture.Result
import dev.bmcreations.expiry.core.architecture.Success
import dev.bmcreations.expiry.features.create.usecases.*
import dev.bmcreations.expiry.models.Bookmark
import dev.bmcreations.expiry.models.Group
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class BookmarkCreateViewModel private constructor(
    private val loadBookmarkUseCase: LoadBookmarkUsecase,
    private val getGroupsUseCase: GetGroupsLiveDataUsecase,
    private val getSelectedGroupUseCase: GetSelectedGroupUsecase,
    private val setSelectedGroupUseCase: SelectGroupUsecase,
    private val createBookmarkUseCase: CreateBookmarkUsecase
) : ViewModel(), CoroutineScope by CoroutineScope(Dispatchers.Main) {

    fun loadBookmark(id: String?, cb: (Bookmark?) -> Unit) {
        loadBookmarkUseCase.execute(id) { cb.invoke(it) }
    }

    fun createBookmark(
        title: String,
        url: String,
        expiration: Date? = null,
        group: Group? = null,
        cb: ((Boolean) -> Unit)
    ) {
        createBookmarkUseCase.execute(title, url, expiration, group) {
            viewModelScope.launch { cb.invoke(it != null) }
        }
    }

    fun selectGroup(group: Group) {
        setSelectedGroupUseCase.execute(group)
    }

    fun selectedGroup(): Group? {
        return getSelectedGroupUseCase.execute()
    }

    suspend fun groups(): LiveData<List<Group>> = getGroupsUseCase.execute()


    companion object {
        fun create(
            loadBookmarkUseCase: LoadBookmarkUsecase,
            getGroupsUseCase: GetGroupsLiveDataUsecase,
            getSelectedGroupUseCase: GetSelectedGroupUsecase,
            setSelectedGroupUseCase: SelectGroupUsecase,
            createBookmarkUseCase: CreateBookmarkUsecase
        ): BookmarkCreateViewModel {
            return BookmarkCreateViewModel(
                loadBookmarkUseCase,
                getGroupsUseCase,
                getSelectedGroupUseCase,
                setSelectedGroupUseCase,
                createBookmarkUseCase
            )
        }
    }
}

data class BookmarkCreateViewState(
    val loading: Boolean = false,
    val request: BookmarkCreateRequest = BookmarkCreateRequest.None,
    val result: BookmarkCreateResult = BookmarkCreateResult.None,
    var error: dev.bmcreations.expiry.core.architecture.Error = dev.bmcreations.expiry.core.architecture.Error()
) {
    sealed class BookmarkCreateRequest : Request() {
        object InitialLoad : BookmarkCreateRequest()
        object None : BookmarkCreateRequest()
    }

    sealed class BookmarkCreateResult : Result() {
        data class Created(val result: Success) : BookmarkCreateResult()
        object None : BookmarkCreateResult()
    }
}
