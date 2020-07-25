package dev.bmcreations.shredder.bar.bookmarks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.bmcreations.shredder.core.extensions.daysBetween
import dev.bmcreations.shredder.core.extensions.startOfDay
import dev.bmcreations.shredder.models.Bookmark
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

/**
 * In memory implementation of our [BookmarksDao]
 */
class InMemoryBookmarksDao : BookmarksDao, CoroutineScope by CoroutineScope(Dispatchers.Main) {

    private val itemsInBar: MutableMap<String, Bookmark> = mutableMapOf()

    private val emitter = MutableLiveData<List<Bookmark>>()

    override suspend fun selectAll(): List<Bookmark> {
        return itemsInBar.values.toList().sortedBy { it.expiresAt }
    }

    override suspend fun selectAllStream(): LiveData<List<Bookmark>> {
        return emitter
    }

    override suspend fun lastCreatedBookmark(): Bookmark? {
        return itemsInBar.values.toList().sortedBy { it.createdAt }.firstOrNull()
    }

    override suspend fun findById(id: String): Bookmark? {
        return itemsInBar.values.find { it.id == id }
    }

    override suspend fun findByUrl(url: String): Bookmark? {
        return itemsInBar.values.find { it.site?.url == url }
    }

    override suspend fun findByLabel(label: String): Bookmark? {
        return itemsInBar.values.find { it.label == label }
    }

    override suspend fun findByExpiration(date: Date): List<Bookmark> {
        return itemsInBar.values.filter { it.expiresAt?.startOfDay()?.daysBetween(date.startOfDay()) == 0 }
    }

    override suspend fun findByExpiration(start: Date, end: Date): List<Bookmark> {
        return itemsInBar.values.filter { it.expiresAt?.after(start) == true && it.expiresAt?.before(end) == true }
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
        launch { emitter.value = selectAll() }
    }
}
