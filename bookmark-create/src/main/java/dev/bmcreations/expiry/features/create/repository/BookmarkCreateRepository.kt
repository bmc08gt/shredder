package dev.bmcreations.expiry.features.create.repository

import androidx.lifecycle.LiveData
import dev.bmcreations.expiry.features.create.view.BookmarkEditData
import dev.bmcreations.expiry.models.Bookmark
import dev.bmcreations.expiry.models.Group
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
