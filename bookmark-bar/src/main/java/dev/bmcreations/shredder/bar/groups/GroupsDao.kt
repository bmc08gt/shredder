package dev.bmcreations.shredder.bar.groups

import androidx.lifecycle.LiveData
import dev.bmcreations.shredder.models.Group

interface GroupsDao {
    suspend fun selectAll(): List<Group>

    suspend fun selectAllStream(): LiveData<List<Group>>

    suspend fun findById(id: String): Group?

    suspend fun findByName(name: String): Group?

    suspend fun upsert(vararg group: Group)

    suspend fun remove(vararg group: Group)

    suspend fun empty()
}
