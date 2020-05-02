package dev.bmcreations.shredder.bar.groups.room

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.bmcreations.shredder.models.Group
import java.util.*

@Dao
interface RoomItemInGroupDao {
    @Delete
    fun delete(vararg group: Group)

    @Query("DELETE FROM groups_table WHERE id = :id")
    fun deleteById(id: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(vararg group: Group)

    @Query("SELECT * FROM groups_table ORDER BY name DESC")
    fun selectAll(): List<Group>

    @Query("SELECT * FROM groups_table ORDER BY name DESC")
    fun observe(): LiveData<List<Group>>

    @Query("SELECT * FROM groups_table WHERE id == :id")
    fun selectById(id: String): Group?

    @Query("SELECT * FROM groups_table WHERE name == :name")
    fun selectByName(name: String): Group?

    @Query("DELETE FROM groups_table")
    fun empty()
}
