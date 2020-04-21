package dev.bmcreations.shredder.bar

import androidx.lifecycle.LiveData
import com.zhuinden.eventemitter.EventSource
import dev.bmcreations.shredder.bar.bookmarks.BookmarksDao
import dev.bmcreations.shredder.bar.groups.GroupsDao
import dev.bmcreations.shredder.models.Bookmark
import dev.bmcreations.shredder.models.Group
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class BookmarksBar(
    private val bookmarkDao: BookmarksDao,
    private val groupsDao: GroupsDao
): CoroutineScope by CoroutineScope(Dispatchers.Main) {

    // get
    suspend fun bookmarks(): List<Bookmark> {
        return bookmarkDao.selectAll()
    }

    suspend fun observeBookmarks(): LiveData<List<Bookmark>> {
        return bookmarkDao.selectAllStream()
    }

    fun groups(): List<Group> {
        val results = mutableListOf<Group>()
        launch(Dispatchers.IO) {
            results.addAll(groupsDao.selectAll())
        }
        return results
    }

    suspend fun observeGroups(): LiveData<List<Group>> {
        return groupsDao.selectAllStream()
    }

     suspend fun findById(id: String): Bookmark? {
        return bookmarkDao.findById(id)
    }

    suspend fun findByUrl(url: String): Bookmark? {
        return bookmarkDao.findByUrl(url)
    }

    suspend fun findByTitle(title: String): Bookmark? {
        return bookmarkDao.findByTitle(title)

    }

    suspend fun findByExpiration(date: Date): List<Bookmark> {
        return bookmarkDao.findByExpiration(date)
    }

    suspend fun findByExpiration(start: Date, end: Date): List<Bookmark> {
        return bookmarkDao.findByExpiration(start, end)
    }

    suspend fun findGroupById(id: String): Group? {
        return groupsDao.findById(id)
    }

    suspend fun findGroupByName(name: String): Group? {
        return groupsDao.findByName(name)
    }
    //endregion

    //put
    fun upsert(vararg bookmark: Bookmark) {
        launch(Dispatchers.IO) {
            bookmarkDao.upsert(*bookmark)
        }
    }
    fun upsert(vararg group: Group) {
        launch(Dispatchers.IO) {
            groupsDao.upsert(*group)
        }
    }
    //endregion

    //remove
    fun remove(vararg bookmark: Bookmark) {
        launch(Dispatchers.IO) {
            bookmarkDao.remove(*bookmark)
        }
    }

    fun remove(id: String) {
        launch(Dispatchers.IO) {
            bookmarkDao.remove(id)
        }
    }
    fun remove(vararg group: Group) {
        launch(Dispatchers.IO) {
            groupsDao.remove(*group)
        }
    }

    fun empty() {
        launch(Dispatchers.IO) {
            bookmarkDao.empty()
            groupsDao.empty()
        }
    }
}
