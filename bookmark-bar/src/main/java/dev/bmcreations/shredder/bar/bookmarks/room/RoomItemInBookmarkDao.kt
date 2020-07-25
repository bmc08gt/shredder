package dev.bmcreations.shredder.bar.bookmarks.room

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.bmcreations.shredder.models.Bookmark
import java.util.*

@Dao
interface RoomItemInBookmarkDao {
    @Delete
    fun delete(vararg bookmark: Bookmark)

    @Query("DELETE FROM bookmarks_table WHERE id = :id")
    fun deleteById(id: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(vararg bookmark: Bookmark)

    @Query("SELECT * FROM bookmarks_table ORDER BY expiresAt DESC")
    fun selectAll(): List<Bookmark>

    @Query("SELECT * FROM bookmarks_table ORDER BY expiresAt DESC")
    fun observe(): LiveData<List<Bookmark>>

    @Query("SELECT * FROM bookmarks_table WHERE id == :id")
    fun selectById(id: String): Bookmark?

    @Query("SELECT * FROM bookmarks_table ORDER BY createdAt DESC")
    fun lastBookmark(): Bookmark?

//    @Query("SELECT * FROM bookmarks_table WHERE url == :url")
//    fun selectByUrl(url: String): Bookmark?

    @Query("SELECT * FROM bookmarks_table WHERE label == :label")
    fun selectByLabel(label: String): Bookmark?

    @Query("SELECT * FROM bookmarks_table WHERE expiresAt == :expiration")
    fun selectByExpiration(expiration: Date): List<Bookmark>

    @Query("SELECT * FROM bookmarks_table WHERE expiresAt BETWEEN :start AND :end")
    fun selectByExpiration(start: Date, end: Date): List<Bookmark>

    @Query("DELETE FROM bookmarks_table")
    fun empty()
}
