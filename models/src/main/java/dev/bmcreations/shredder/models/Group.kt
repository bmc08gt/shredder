package dev.bmcreations.shredder.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import dev.bmcreations.graphql.model.GraphResult
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "groups_table")
data class Group(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    @TypeConverters(DateConverter::class)
    val createdAt: Date = Date(),
    @TypeConverters(DateConverter::class)
    val updatedAt: Date = createdAt,
    @TypeConverters(BookmarkConverter::class)
    val bookmarks: List<Bookmark> = emptyList()
): Parcelable

class CreateGroupResponse : GraphResult<CreateGroupResult, Group>() {
    override fun hasData(): Boolean = data?.createGroup != null
    override fun result(): Group? = data?.createGroup
}
data class CreateGroupResult(val createGroup: Group)

class UpdateGroupResponse : GraphResult<UpdateGroupResult, Group>() {
    override fun hasData(): Boolean = data?.updateGroup != null
    override fun result(): Group? = data?.updateGroup
}
data class UpdateGroupResult(val updateGroup: Group)

class DeleteGroupResponse : GraphResult<DeleteGroupResult, Boolean>() {
    override fun hasData(): Boolean = data?.deleteGroup != null
    override fun result(): Boolean? = data?.deleteGroup
}
data class DeleteGroupResult(val deleteGroup: Boolean)
