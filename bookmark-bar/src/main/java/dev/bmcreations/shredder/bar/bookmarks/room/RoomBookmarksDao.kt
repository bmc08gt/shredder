package dev.bmcreations.shredder.bar.bookmarks.room

import androidx.lifecycle.LiveData
import dev.bmcreations.shredder.bar.bookmarks.BookmarksDao
import dev.bmcreations.shredder.bar.db.BookmarksDatabase
import dev.bmcreations.shredder.models.Bookmark
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.util.*

class RoomBookmarksDao(db: BookmarksDatabase) : BookmarksDao, CoroutineScope by CoroutineScope(Dispatchers.IO) {

    private val dao = db.itemsInBookmarksDao()

    override suspend fun selectAll(): List<Bookmark> {
        return dao.selectAll()
    }

    override suspend fun selectAllStream(): LiveData<List<Bookmark>> {
        return dao.observe()
    }

    override suspend fun lastCreatedBookmark(): Bookmark? {
        return dao.lastBookmark()
    }

    override suspend fun findById(id: String): Bookmark? {
        return dao.selectById(id)
    }

    override suspend fun findByUrl(url: String): Bookmark? {
        return null
    }

    override suspend fun findByLabel(label: String): Bookmark? {
        return dao.selectByLabel(label)
    }

    override suspend fun findByExpiration(date: Date): List<Bookmark> {
        return dao.selectByExpiration(date)
    }

    override suspend fun findByExpiration(start: Date, end: Date): List<Bookmark> {
        return dao.selectByExpiration(start, end)
    }

    override suspend fun upsert(vararg bookmark: Bookmark) {
        dao.upsert(*bookmark)
    }

    override suspend fun remove(vararg bookmark: Bookmark) {
        dao.delete(*bookmark)
    }

    override suspend fun remove(id: String) {
        dao.deleteById(id)
    }

    override suspend fun empty() {
        dao.empty()
    }
}
