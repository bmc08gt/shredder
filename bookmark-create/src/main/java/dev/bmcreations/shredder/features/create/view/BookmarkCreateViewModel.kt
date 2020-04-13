package dev.bmcreations.shredder.features.create.view

import androidx.lifecycle.LiveData
import dev.bmcreations.shredder.core.architecture.*
import dev.bmcreations.shredder.features.create.usecases.*
import dev.bmcreations.shredder.features.create.view.calendar.toDate
import dev.bmcreations.shredder.models.Bookmark
import dev.bmcreations.shredder.models.Group
import dev.bmcreations.shredder.models.Website
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.threeten.bp.LocalDate
import java.util.*

class BookmarkCreateViewModel private constructor(
    private val loadBookmarkUseCase: LoadBookmarkUsecase,
    private val getGroupsUseCase: GetGroupsLiveDataUsecase,
    private val getSelectedGroupUseCase: GetSelectedGroupUsecase,
    private val setSelectedGroupUseCase: SelectGroupUsecase,
    private val getTitleUseCase: GetTitleUsecase,
    private val setTitleUseCase: SetTitleUsecase,
    private val getUrlUseCase: GetUrlUsecase,
    private val setUrlUseCase: SetUrlUsecase,
    private val getExpirationDateUseCase: GetExpirationDateUsecase,
    private val setExpirationDateUseCase: SetExpirationDateUsecase,
    private val createBookmarkUseCase: CreateBookmarkUsecase
) : BaseViewModel<BookmarkCreateViewState, BookmarkCreateEvent, BookmarkCreateEffect>(),
    CoroutineScope by CoroutineScope(Dispatchers.Main) {

    init {
        state.value = BookmarkCreateViewState(Loading(loading = true))
    }

    fun createBookmark() {
        createBookmarkUseCase.execute {
            if (it != null) {
                eventEmitter.emit(BookmarkCreateEvent.Created(it))
            } else {
                informOfError(title = "Error", message = "Failed to create bookmark.")
            }
        }
    }

    fun loadBookmark(id: String?) {
        loadBookmarkUseCase.execute(id) { populate() }
    }

    fun selectGroup(group: Group) {
        setSelectedGroupUseCase.execute(group)
        populate()
    }

    fun selectedGroup(): Group? {
        return getSelectedGroupUseCase.execute()
    }

    suspend fun groups(): LiveData<List<Group>> = getGroupsUseCase.execute()

    fun onExpirationSet(date: LocalDate?) {
        date?.toDate().apply {
            setExpirationDateUseCase.execute(this)
            populate()
        }
    }

    fun updateLabel(text: String?) {
        setTitleUseCase.execute(text)
    }

    fun updateUrl(url: String?) {
        setUrlUseCase.execute(url)
    }

    private fun populate() {
        val edits = BookmarkEditData(
            title = getTitleUseCase.execute(),
            url = getUrlUseCase.execute(),
            group = getSelectedGroupUseCase.execute(),
            expiration = getExpirationDateUseCase.execute()
        )
        state.value = getLastState()?.copy(
            loading = Loading(),
            error = Error(),
            data = edits
        )
    }

    override fun informOfError(
        exception: Throwable?,
        title: String?,
        message: String?
    ) {
        state.value = getLastState()?.copy(
            loading = Loading(),
            error = Error(exception, title, message)
        )
    }

    override fun informOfError(
        exception: Throwable?,
        titleResId: Int?,
        messageResId: Int?
    ) {
        state.value = getLastState()?.copy(
            loading = Loading(),
            error = Error(
                exception = exception,
                titleResId = titleResId,
                messageResId = messageResId
            )
        )
    }

    companion object {
        fun create(
            loadBookmarkUseCase: LoadBookmarkUsecase,
            getGroupsUseCase: GetGroupsLiveDataUsecase,
            getSelectedGroupUseCase: GetSelectedGroupUsecase,
            setSelectedGroupUseCase: SelectGroupUsecase,
            getTitleUseCase: GetTitleUsecase,
            setTitleUseCase: SetTitleUsecase,
            getUrlUseCase: GetUrlUsecase,
            setUrlUseCase: SetUrlUsecase,
            getExpirationDateUseCase: GetExpirationDateUsecase,
            setExpirationDateUseCase: SetExpirationDateUsecase,
            createBookmarkUseCase: CreateBookmarkUsecase
        ): BookmarkCreateViewModel {
            return BookmarkCreateViewModel(
                loadBookmarkUseCase,
                getGroupsUseCase,
                getSelectedGroupUseCase,
                setSelectedGroupUseCase,
                getTitleUseCase,
                setTitleUseCase,
                getUrlUseCase,
                setUrlUseCase,
                getExpirationDateUseCase,
                setExpirationDateUseCase,
                createBookmarkUseCase
            )
        }
    }
}

data class BookmarkEditData(
    val title: String? = null,
    val url: String? = null,
    val group: Group? = null,
    val expiration: Date? = null
)

fun Bookmark?.toEditData(): BookmarkEditData {
    if (this == null) {
        return BookmarkEditData()
    }

    return BookmarkEditData(
        title = title,
        url = site?.url,
        group = group,
        expiration = expiration
    )
}

fun BookmarkEditData.createBookmark(): Bookmark {
    return Bookmark(
        title = title,
        site = url?.let { Website(it) },
        expiration = expiration,
        group = group
    )
}

data class BookmarkCreateViewState(
    override val loading: Loading = Loading(),
    override val error: Error = Error(),
    val data: BookmarkEditData = BookmarkEditData()
) : ViewState()

sealed class BookmarkCreateEvent : ViewStateEvent() {
    data class Created(val bookmark: Bookmark?) : BookmarkCreateEvent()
}

sealed class BookmarkCreateEffect : ViewStateEffect()
