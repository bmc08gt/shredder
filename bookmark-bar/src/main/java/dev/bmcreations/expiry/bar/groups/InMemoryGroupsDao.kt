package dev.bmcreations.expiry.bar.groups

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.bmcreations.expiry.models.Group
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InMemoryGroupsDao: GroupsDao, CoroutineScope by CoroutineScope(Dispatchers.Main) {

    private val groupsInBar: MutableMap<String, Group> = mutableMapOf()

    private val emitter = MutableLiveData<List<Group>>()

    override suspend fun selectAll(): List<Group> {
        return groupsInBar.values.toList().sortedBy { it.name }
    }

    override suspend fun selectAllStream(): LiveData<List<Group>> {
        return emitter
    }

    override suspend fun findById(id: String): Group? {
        return groupsInBar.values.find { it.id == id }
    }

    override suspend fun findByName(name: String): Group? {
        return groupsInBar.values.find { it.name == name }
    }

    override suspend fun upsert(vararg group: Group) {
        group.forEach {
            groupsInBar[it.id] = it
        }
        emit()
    }

    override suspend fun remove(vararg group: Group) {
        group.forEach {
            groupsInBar.remove(it.id)
        }
        emit()
    }

    override suspend fun empty() {
        groupsInBar.clear()
        emit()
    }

    private fun emit() {
        launch { emitter.value = selectAll() }
    }
}
