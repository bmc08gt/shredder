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
    private val loadBookmark: LoadBookmarkUseCase,
    private val getGroups: GetGroupsLiveDataUseCase,
    private val getSelectedGroup: GetSelectedGroupUseCase,
    private val setSelectedGroup: SelectGroupUseCase,
    private val getLabel: GetLabelUseCase,
    private val setLabel: SetLabelUseCase,
    private val getUrl: GetUrlUseCase,
    private val setUrl: SetUrlUseCase,
    private val getExpirationDate: GetExpirationDateUseCase,
    private val setExpirationDate: SetExpirationDateUseCase,
    private val createBookmark: CreateBookmarkUseCase
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
        createBookmark {
            if (it != null) {
                //eventEmitter.emit(BookmarkCreateEvent.Created(it))
            } else {
                informOfError(title = "Error", message = "Failed to create bookmark.")
            }
        }
    }

    private fun loadBookmark(id: String?) = loadBookmark(id) { populate() }

    private fun selectGroup(group: Group) {
        setSelectedGroup(group)
        populate()
    }

    fun selectedGroup(): Group? = getSelectedGroup()

    suspend fun groups(): LiveData<List<Group>> = getGroups()

    private fun onExpirationSet(date: LocalDate?) {
        with(date?.toDate()) {
            setExpirationDate(this)
            populate()
        }
    }

    private fun updateLabel(text: String?) {
        setLabel(text)
    }

    private fun updateUrl(url: String?) {
        setUrl(url)
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
                    label = getLabel(),
                    url = getUrl(),
                    group = getSelectedGroup(),
                    expiresAt = getExpirationDate(),
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
            loadBookmarkUseCase: LoadBookmarkUseCase,
            getGroupsUseCase: GetGroupsLiveDataUseCase,
            getSelectedGroupUseCase: GetSelectedGroupUseCase,
            setSelectedGroupUseCase: SelectGroupUseCase,
            getTitleUseCase: GetLabelUseCase,
            setTitleUseCase: SetLabelUseCase,
            getUrlUseCase: GetUrlUseCase,
            setUrlUseCase: SetUrlUseCase,
            getExpirationDateUseCase: GetExpirationDateUseCase,
            setExpirationDateUseCase: SetExpirationDateUseCase,
            createBookmarkUseCase: CreateBookmarkUseCase
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
    val label: String? = null,
    val url: String? = null,
    val group: Group? = null,
    val groups: List<Group> = emptyList(),
    val expiresAt: Date? = null
)

fun Bookmark?.toEditData(): BookmarkEditData {
    if (this == null) {
        return BookmarkEditData()
    }

    return BookmarkEditData(
        label = label,
        url = site?.url,
        group = group,
        expiresAt = expiresAt
    )
}

fun BookmarkEditData.createBookmark(): Bookmark {
    return Bookmark(
        label = label,
        site = url?.let { Website(it) },
        expiresAt = expiresAt,
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
