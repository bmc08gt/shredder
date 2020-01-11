package dev.bmcreations.expiry.features.create.repository

import androidx.lifecycle.LiveData
import dev.bmcreations.expiry.models.Bookmark
import dev.bmcreations.expiry.models.Group
import java.util.*

interface BookmarkCreateRepository {
    suspend fun groups(): LiveData<List<Group>>
    fun selectGroup(group: Group)
    fun selectedGroup(): Group?
    fun createBookmark(
        title: String,
        url: String,
        expiration: Date? = null,
        group: Group? = null,
        cb: (Bookmark?) -> Unit
    )
    fun loadBookmark(id: String?, cb: (Bookmark?) -> Unit)
}
