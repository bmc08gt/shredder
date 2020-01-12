package dev.bmcreations.expiry.features.create.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dev.bmcreations.expiry.core.architecture.*
import dev.bmcreations.expiry.features.create.usecases.*
import dev.bmcreations.expiry.features.create.view.BookmarkCreateViewState.StateRequest
import dev.bmcreations.expiry.features.create.view.BookmarkCreateViewState.StateResult
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
) : BaseViewModel<BookmarkCreateViewState>(),
    ViewStateRequestImpl<StateRequest>,
    ViewStateResultImpl<StateResult>, CoroutineScope by CoroutineScope(Dispatchers.Main) {

    val state: MutableLiveData<Event<BookmarkCreateViewState>> = MutableLiveData()

    init {
        state.value = Event(BookmarkCreateViewState(loading = true))
    }

    fun createBookmark(
        title: String,
        url: String,
        expiration: Date? = null,
        group: Group? = null
    ) {
        createBookmarkUseCase.execute(title, url, expiration, group) {
            viewModelScope.launch {
                if (it != null ) {
                    informOfResult(StateResult.Created(Success()))
                } else {
                    informOfError(title = "Error", message = "Failed to create bookmark.")
                }
            }
        }
    }

    fun loadBookmark(id: String?) {
        loadBookmarkUseCase.execute(id) {

        }
    }

    fun selectGroup(group: Group) {
        setSelectedGroupUseCase.execute(group)
    }

    fun selectedGroup(): Group? {
        return getSelectedGroupUseCase.execute()
    }

    suspend fun groups(): LiveData<List<Group>> = getGroupsUseCase.execute()

    override fun informOfResult(result: StateResult) {
        state.value = Event(
            BookmarkCreateViewState(
                loading = false,
                request = StateRequest.None,
                result = result
            )
        )
    }

    override fun informOfRequest(request: StateRequest) {
        state.value = Event(
            BookmarkCreateViewState(
                loading = false,
                request = request,
                result = StateResult.None
            )
        )
    }

    override fun informOfError(
        exception: Throwable?,
        title: String?,
        message: String?
    ) {
        state.value = Event(
            BookmarkCreateViewState(
                loading = false,
                request = StateRequest.None,
                result = StateResult.None,
                error = Error(exception, title, message)
            )
        )
    }

    override fun informOfError(
        exception: Throwable?,
        titleResId: Int?,
        messageResId: Int?
    ) {
        state.value = Event(
            BookmarkCreateViewState(
                loading = false,
                request = StateRequest.None,
                result = StateResult.None,
                error = Error(
                    exception = exception,
                    titleResId = titleResId,
                    messageResId = messageResId
                )
            )
        )
    }

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
    override val loading: Boolean = false,
    override val request: StateRequest = StateRequest.None,
    override val result: StateResult = StateResult.None,
    override val error: Error = Error()
) : ViewState() {
    sealed class StateRequest : ViewStateRequest() {
        data class InitialLoad(val bookmarkId: String? = null) : StateRequest()
        object None : StateRequest()
    }

    sealed class StateResult : ViewStateResult() {
        data class Created(val result: Success) : StateResult()
        object None : StateResult()
    }
}
