package dev.bmcreations.shredder.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.TypeConverters
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "bookmarks_table")
data class Bookmark(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    @TypeConverters(SiteConverter::class)
    val site: Website?,
    val title: String?,
    @TypeConverters(DateConverter::class)
    val expiration: Date? = null,
    @TypeConverters(GroupConverter::class)
    val group: Group? = null,
    val createdAt: Date? = Date()
) : Parcelable

