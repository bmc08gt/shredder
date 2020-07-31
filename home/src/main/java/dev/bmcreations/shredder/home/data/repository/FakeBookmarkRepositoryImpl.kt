package dev.bmcreations.shredder.home.data.repository

import dev.bmcreations.shredder.models.Bookmark
import dev.bmcreations.shredder.models.Website
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

class FakeBookmarkRepositoryImpl : BookmarkRepository, CoroutineScope by CoroutineScope(Dispatchers.Main) {

    private var initialList = mutableListOf(
        Bookmark(site = Website("https://planoly.com"), label = "Planoly"),
        Bookmark(site = Website("https://google.com"), label = "Google"),
        Bookmark(site = Website("https://twitter.com"), label = "Twitter"),
        Bookmark(site = Website("https://slack.com"), label = "Slack")
    )

    private var bookmarks = mutableMapOf<String, Bookmark>()

    private val channel  = ConflatedBroadcastChannel<List<Bookmark>>()

    init {
        initialList.forEach {
            bookmarks[it.id] = it
        }
        channel.offer(bookmarks.values.toList())
    }

    override fun loadBookmarks(): Flow<List<Bookmark>> = channel.asFlow()

    override fun loadBookmark(bookmarkId: String?, callback: (Result<Bookmark>) -> Unit) {
        val result = bookmarks[bookmarkId]
        callback(when {
            result != null -> Result.success(result)
            else -> Result.failure(Throwable("No bookmark found for ID"))
        })
    }

    override fun upsertBookmark(upsert: Bookmark): Boolean {
        bookmarks[upsert.id] = upsert

        launch(Dispatchers.Main) { channel.send(bookmarks.values.toList()) }

        return bookmarks.containsKey(upsert.id)
    }
}
