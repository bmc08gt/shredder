package dev.bmcreations.expiry.bar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.bmcreations.expiry.core.extensions.daysBetween
import dev.bmcreations.expiry.core.extensions.now
import dev.bmcreations.expiry.core.extensions.plusDays
import dev.bmcreations.expiry.core.extensions.startOfDay
import dev.bmcreations.expiry.models.Bookmark
import dev.bmcreations.expiry.models.Website
import java.util.*

/**
 * In memory implementation of our [BookmarksDao]
 */
class InMemoryBookmarksDao : BookmarksDao {

    private val itemsInBar: MutableMap<String, Bookmark> = mutableMapOf()

    private val emitter = MutableLiveData<List<Bookmark>>()

    init {
        itemsInBar.apply {
            for (i in 0 until 5) {
                Bookmark(
                    title = "bookmark $i",
                    site = Website(
                        url = "https://bmcreations.dev"
                    ),
                    expiration = now.plusDays(60 + i)
                ).also {
                    put(it.id, it)
                }
            }
        }
    }

    override suspend fun selectAll(): List<Bookmark> {
        return itemsInBar.values.toList().sortedBy { it.expiration }
    }

    override suspend fun selectAllStream(): LiveData<List<Bookmark>> {
        return emitter
    }

    override suspend fun findByUrl(url: String): Bookmark? {
        return itemsInBar.values.find { it.site?.url == url }
    }

    override suspend fun findByTitle(title: String): Bookmark? {
        return itemsInBar.values.find { it.title == title }
    }

    override suspend fun findByExpiration(date: Date): List<Bookmark> {
        return itemsInBar.values.filter { it.expiration?.startOfDay()?.daysBetween(date.startOfDay()) == 0 }
    }

    override suspend fun findByExpiration(start: Date, end: Date): List<Bookmark> {
        return itemsInBar.values.filter { it.expiration?.after(start) == true && it.expiration?.before(end) == true }
    }

    override suspend fun upsert(bookmark: Bookmark) {
        itemsInBar[bookmark.id] = bookmark
        emit()
    }

    override suspend fun remove(bookmark: Bookmark) {
        itemsInBar.remove(bookmark.id)
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

    private suspend fun emit() {
        emitter.value = selectAll()
    }
}
