package dev.bmcreations.shredder.bar.groups.room

import androidx.lifecycle.LiveData
import dev.bmcreations.shredder.bar.db.BookmarksDatabase
import dev.bmcreations.shredder.bar.groups.GroupsDao
import dev.bmcreations.shredder.models.Group
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class RoomGroupDao(db: BookmarksDatabase): GroupsDao, CoroutineScope by CoroutineScope(Dispatchers.IO) {

    private val dao = db.itemsInGroupsDao()

    override suspend fun selectAll(): List<Group> {
        return dao.selectAll()
    }

    override suspend fun selectAllStream(): LiveData<List<Group>> {
        return dao.observe()
    }

    override suspend fun findById(id: String): Group? {
        return dao.selectById(id)
    }

    override suspend fun findByName(name: String): Group? {
        return dao.selectByName(name)
    }

    override suspend fun upsert(vararg group: Group) {
        dao.upsert(*group)
    }

    override suspend fun remove(vararg group: Group) {
        dao.delete(*group)
    }

    override suspend fun empty() {
        dao.empty()
    }
}
