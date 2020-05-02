package dev.bmcreations.shredder.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
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
