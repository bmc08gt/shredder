package dev.bmcreations.shredder.features.create.repository

import androidx.lifecycle.LiveData
import dev.bmcreations.shredder.bar.BookmarksComponent
import dev.bmcreations.shredder.features.create.view.BookmarkEditData
import dev.bmcreations.shredder.features.create.view.createBookmark
import dev.bmcreations.shredder.features.create.view.toEditData
import dev.bmcreations.shredder.models.Bookmark
import dev.bmcreations.shredder.models.Group
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class BookmarkCreateRepositoryImpl(
    private val bookmarkComponent: BookmarksComponent
): BookmarkCreateRepository, CoroutineScope by CoroutineScope(Dispatchers.Main) {

    private var editData = BookmarkEditData()

    override suspend fun groups(): LiveData<List<Group>> {
        return bookmarkComponent.bar.observeGroups()
    }

    override fun selectGroup(group: Group) {
       editData = editData.copy(group = group)
    }

    override fun selectedGroup(): Group? = editData.group

    override fun getTitle(): String? = editData.title

    override fun setTitle(title: String?) {
        editData = editData.copy(title = title)
    }

    override fun getUrl(): String? = editData.url

    override fun setUrl(url: String?) {
        editData = editData.copy(url = url)
    }

    override fun getExpirationDate(): Date? = editData.expiration

    override fun setExpirationDate(date: Date?) {
        editData = editData.copy(expiration = date)
    }

    override fun createBookmark(cb: (Bookmark?) -> Unit) {
        val created = editData.createBookmark()
        bookmarkComponent.bar.upsert(created)
        launch(Dispatchers.IO) {
            val result = bookmarkComponent.bar.findById(created.id)
            launch(Dispatchers.Main) { cb.invoke(result) }
        }
    }

    override fun loadBookmark(id: String?, cb: (BookmarkEditData) -> Unit) {
        launch(Dispatchers.IO) {
            editData = with(bookmarkComponent.bar.findById(id ?: "")) {
                this.toEditData()
            }
            launch(Dispatchers.Main) { cb.invoke(editData) }
        }
    }
}
