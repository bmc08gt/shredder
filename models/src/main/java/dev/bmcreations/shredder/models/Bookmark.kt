package dev.bmcreations.shredder.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import dev.bmcreations.graphql.model.GraphResult
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "bookmarks_table")
data class Bookmark(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    @TypeConverters(SiteConverter::class)
    val site: Website?,
    val label: String?,
    @TypeConverters(DateConverter::class)
    val expiresAt: Date? = null,
    @TypeConverters(GroupConverter::class)
    val group: Group? = null,
    val createdAt: Date? = Date()
) : Parcelable


class CreateBookmarkResponse : GraphResult<CreateBookmarkResult, Bookmark>() {
    override fun hasData(): Boolean = data?.createBookmark != null
    override fun result(): Bookmark? = data?.createBookmark
}
data class CreateBookmarkResult(val createBookmark: Bookmark)

class UpdateBookmarkResponse : GraphResult<UpdateBookmarkResult, Bookmark>() {
    override fun hasData(): Boolean = data?.updateBookmark != null
    override fun result(): Bookmark? = data?.updateBookmark
}
data class UpdateBookmarkResult(val updateBookmark: Bookmark)

class DeleteBookmarkResponse : GraphResult<DeleteBookmarkResult, Boolean>() {
    override fun hasData(): Boolean = data?.deleteBookmark != null
    override fun result(): Boolean? = data?.deleteBookmark
}
data class DeleteBookmarkResult(val deleteBookmark: Boolean)
