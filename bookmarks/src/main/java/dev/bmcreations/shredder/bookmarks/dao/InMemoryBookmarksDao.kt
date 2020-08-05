package dev.bmcreations.shredder.bookmarks.dao

import dev.bmcreations.shredder.models.Bookmark
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


/**
 * In memory implementation of our [BookmarksDao]
 */
class InMemoryBookmarksDao : BookmarksDao, CoroutineScope by CoroutineScope(Dispatchers.Main) {

    private val itemsInBar: MutableMap<String, Bookmark> = mutableMapOf()

    private val channel = MutableStateFlow<List<Bookmark>>(emptyList())

    override suspend fun selectAll(): List<Bookmark> {
        return itemsInBar.values.toList()
    }

    override suspend fun lastCreatedBookmark(): Bookmark? {
        return itemsInBar.values.toList().minByOrNull { it.createdAt }
    }

    override fun findById(id: String, callback: (Result<Bookmark>) -> Unit) {
        val bookmark = itemsInBar.values.find { it.id == id }
        if (bookmark == null) {
            callback(Result.failure(Throwable("Unable to find bookmark with ID=$id")))
        } else {
            callback(Result.success(bookmark))
        }
    }

    override fun findByLabel(label: String, callback: (Result<Bookmark>) -> Unit) {
        val bookmark = itemsInBar.values.find { it.label == label }
        if (bookmark == null) {
            callback(Result.failure(Throwable("Unable to find bookmark with label=$label")))
        } else {
            callback(Result.success(bookmark))
        }
    }

    override fun findByUrl(url: String, callback: (Result<Bookmark>) -> Unit) {
        val bookmark = itemsInBar.values.find { it.site.url == url }
        if (bookmark == null) {
            callback(Result.failure(Throwable("Unable to find bookmark with URL=$url")))
        } else {
            callback(Result.success(bookmark))
        }
    }

    override suspend fun upsert(vararg bookmark: Bookmark) {
        bookmark.forEach { itemsInBar[it.id] = it }
        emit()
    }

    override suspend fun remove(vararg bookmark: Bookmark) {
        bookmark.forEach { itemsInBar.remove(it.id) }
        emit()
    }

    override suspend fun remove(id: String) {
        itemsInBar.remove(id)
        emit()
    }

    override suspend fun empty() {
        itemsInBar.clear()
        emit()
    }

    private fun emit() {
        launch { channel.value = selectAll() }
    }
}