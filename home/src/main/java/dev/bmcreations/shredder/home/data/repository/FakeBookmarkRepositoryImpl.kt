package dev.bmcreations.shredder.home.data.repository

import dev.bmcreations.shredder.models.Bookmark
import dev.bmcreations.shredder.models.Website
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import java.util.*

class FakeBookmarkRepositoryImpl : BookmarkRepository,
                                   CoroutineScope by CoroutineScope(Dispatchers.Main) {

    private var initialList = mutableListOf(
        Bookmark(site = Website("https://planoly.com"), label = "Planoly"),
        Bookmark(site = Website("https://google.com"), label = "Google"),
        Bookmark(site = Website("https://twitter.com"), label = "Twitter"),
        Bookmark(site = Website("https://slack.com"), label = "Slack")
    )

    init {
        launch {
            withContext(Dispatchers.IO) {
                for (i in 0..12) {
                    delay(100)
                    val bookmark = Bookmark(label = "test$i", site = Website(url = "reddit.com"))
                    upsertBookmark(bookmark)
                }
            }
        }
    }

    private var bookmarks = mutableMapOf<String, Bookmark>()

    private val channel = MutableStateFlow<List<Bookmark>>(emptyList())

    override fun loadBookmarks(): List<Bookmark> = channel.value

    override fun loadBookmark(bookmarkId: String?, callback: (Result<Bookmark>) -> Unit) {
        val result = bookmarks[bookmarkId]
        callback(
            when {
                result != null -> Result.success(result)
                else -> Result.failure(Throwable("No bookmark found for ID"))
            }
        )
    }

    override fun upsertBookmark(upsert: Bookmark): Boolean {
        bookmarks[upsert.id] = upsert
        dispatchChange()
        return bookmarks.containsKey(upsert.id)
    }

    override fun removeBookmark(removal: Bookmark): Boolean {
        val removed = bookmarks.remove(removal.id) != null
        dispatchChange()
        return removed
    }

    private fun dispatchChange() {
        channel.value = (bookmarks.values.toList())
    }
}
