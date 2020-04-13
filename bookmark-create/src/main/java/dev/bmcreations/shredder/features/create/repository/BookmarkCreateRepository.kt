package dev.bmcreations.shredder.features.create.repository

import androidx.lifecycle.LiveData
import dev.bmcreations.shredder.features.create.view.BookmarkEditData
import dev.bmcreations.shredder.models.Bookmark
import dev.bmcreations.shredder.models.Group
import java.util.*

interface BookmarkCreateRepository {
    suspend fun groups(): LiveData<List<Group>>
    fun selectGroup(group: Group)
    fun selectedGroup(): Group?
    fun getTitle(): String?
    fun setTitle(title: String?)
    fun getUrl(): String?
    fun setUrl(url: String?)
    fun getExpirationDate(): Date?
    fun setExpirationDate(date: Date?)
    fun createBookmark(cb: (Bookmark?) -> Unit)
    fun loadBookmark(id: String?, cb: (BookmarkEditData) -> Unit)
}
