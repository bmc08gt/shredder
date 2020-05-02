package dev.bmcreations.shredder.features.create.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import dev.bmcreations.shredder.core.architecture.*
import dev.bmcreations.shredder.features.create.usecases.*
import dev.bmcreations.shredder.features.create.view.calendar.toDate
import dev.bmcreations.shredder.models.Bookmark
import dev.bmcreations.shredder.models.Group
import dev.bmcreations.shredder.models.Website
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
) : BaseViewModel<BookmarkCreateViewState, BookmarkCreateEvent, BookmarkCreateEffect>(
    BookmarkCreateViewState(ViewStateLoading(loading = true))
), CoroutineScope by CoroutineScope(Dispatchers.Main) {

    override fun process(event: BookmarkCreateEvent) {
        when (event) {
            BookmarkCreateEvent.Create -> createBookmark()
            is BookmarkCreateEvent.LoadBookmark -> loadBookmark(event.id)
            is BookmarkCreateEvent.SelectGroup -> selectGroup(event.group)
            is BookmarkCreateEvent.ExpirationSet -> onExpirationSet(event.date)
            is BookmarkCreateEvent.LabelUpdated -> updateLabel(event.text)
            is BookmarkCreateEvent.UrlUpdated -> updateUrl(event.url)
            is BookmarkCreateEvent.GroupsUpdated -> updateGroups(event.groups)
        }
    }

    private fun createBookmark() {
        createBookmarkUseCase.execute {
            if (it != null) {
                //eventEmitter.emit(BookmarkCreateEvent.Created(it))
            } else {
                informOfError(title = "Error", message = "Failed to create bookmark.")
            }
        }
    }

    private fun loadBookmark(id: String?) {
        loadBookmarkUseCase.execute(id) { populate() }
    }

    private fun selectGroup(group: Group) {
        setSelectedGroupUseCase.execute(group)
        populate()
    }

    fun selectedGroup(): Group? {
        return getSelectedGroupUseCase.execute()
    }

    suspend fun groups(): LiveData<List<Group>> = getGroupsUseCase.execute()

    private fun onExpirationSet(date: LocalDate?) {
        date?.toDate().apply {
            setExpirationDateUseCase.execute(this)
            populate()
        }
    }

    private fun updateLabel(text: String?) {
        setTitleUseCase.execute(text)
    }

    private fun updateUrl(url: String?) {
        setUrlUseCase.execute(url)
    }

    private fun updateGroups(groups: List<Group>) {
        populate(groups)
    }

    private fun populate(updatedGroups: List<Group>? = null) {
        setState {
            val existingGroups = data.groups
            copy(
                loading = ViewStateLoading(),
                error = ViewStateError(),
                data = data.copy(
                    title = getTitleUseCase.execute(),
                    url = getUrlUseCase.execute(),
                    group = getSelectedGroupUseCase.execute(),
                    expiration = getExpirationDateUseCase.execute(),
                    groups = updatedGroups ?: existingGroups
                )
            )
        }
    }

    override fun informOfLoading(message: String) {
        viewModelScope.launch(Dispatchers.Main) {
            setState {
                copy(
                    loading = ViewStateLoading(loading = true, message = message)
                )
            }
        }
    }

    override fun informOfError(exception: Throwable?, title: String?, message: String?) {
        viewModelScope.launch(Dispatchers.Main) {
            setState {
                copy(
                    loading = ViewStateLoading(),
                    error = generateError(exception, title, message)
                )
            }
        }
    }

    override fun informOfError(exception: Throwable?, titleResId: Int?, messageResId: Int?) {
        viewModelScope.launch(Dispatchers.Main) {
            setState {
                copy(
                    loading = ViewStateLoading(),
                    error = generateError(exception, titleResId, messageResId)
                )
            }
        }
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
    val groups: List<Group> = emptyList(),
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
    override val loading: ViewStateLoading = ViewStateLoading(),
    override val error: ViewStateError = ViewStateError(),
    val data: BookmarkEditData = BookmarkEditData()
) : ViewState

sealed class BookmarkCreateEvent : ViewStateEvent() {
    object Create : BookmarkCreateEvent()
    data class LoadBookmark(val id: String?): BookmarkCreateEvent()
    data class SelectGroup(val group: Group): BookmarkCreateEvent()
    data class GroupsUpdated(val groups: List<Group>): BookmarkCreateEvent()
    data class ExpirationSet(val date: LocalDate?): BookmarkCreateEvent()
    data class LabelUpdated(val text: String?): BookmarkCreateEvent()
    data class UrlUpdated(val url: String?): BookmarkCreateEvent()
}

sealed class BookmarkCreateEffect : ViewStateEffect()
