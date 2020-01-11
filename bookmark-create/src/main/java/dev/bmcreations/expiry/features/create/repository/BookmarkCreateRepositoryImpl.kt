package dev.bmcreations.expiry.features.create.repository

import androidx.lifecycle.LiveData
import dev.bmcreations.expiry.bar.BookmarksComponent
import dev.bmcreations.expiry.models.Bookmark
import dev.bmcreations.expiry.models.Group
import dev.bmcreations.expiry.models.Website
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class BookmarkCreateRepositoryImpl(
    private val bookmarkComponent: BookmarksComponent
): BookmarkCreateRepository, CoroutineScope by CoroutineScope(Dispatchers.Main) {

    private var selectedGroup: Group? = null

    override suspend fun groups(): LiveData<List<Group>> {
        return bookmarkComponent.bar.observeGroups()
    }

    override fun selectGroup(group: Group) {
       selectedGroup = group
    }

    override fun selectedGroup(): Group? {
        return selectedGroup
    }

    override fun createBookmark(title: String, url: String, expiration: Date?, group: Group?, cb: (Bookmark?) -> Unit) {
        val created = Bookmark(title = title, site = Website(url), expiration = expiration, group = group)
        bookmarkComponent.bar.upsert(created)
        launch(Dispatchers.IO) {
            cb.invoke(bookmarkComponent.bar.findById(created.id))
        }
    }

    override fun loadBookmark(id: String?, cb: (Bookmark?) -> Unit) {
        launch(Dispatchers.IO) {
            cb.invoke(bookmarkComponent.bar.findById(id ?: ""))
        }
    }
}
