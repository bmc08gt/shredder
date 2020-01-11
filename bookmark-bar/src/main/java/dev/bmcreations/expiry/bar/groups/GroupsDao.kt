package dev.bmcreations.expiry.bar.groups

import androidx.lifecycle.LiveData
import dev.bmcreations.expiry.models.Bookmark
import dev.bmcreations.expiry.models.Group
import java.util.*

interface GroupsDao {
    suspend fun selectAll(): List<Group>

    suspend fun selectAllStream(): LiveData<List<Group>>

    suspend fun findById(id: String): Group?

    suspend fun findByName(name: String): Group?

    suspend fun upsert(vararg group: Group)

    suspend fun remove(vararg group: Group)

    suspend fun empty()
}
