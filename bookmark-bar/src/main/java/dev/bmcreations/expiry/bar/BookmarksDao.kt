package dev.bmcreations.expiry.bar

import androidx.lifecycle.LiveData
import dev.bmcreations.expiry.models.Bookmark
import java.util.*

/**
 * All actions to access and modify our [Bookmark] database.
 */
interface BookmarksDao {

    suspend fun selectAll(): List<Bookmark>

    suspend fun selectAllStream(): LiveData<List<Bookmark>>

    suspend fun findByUrl(url: String): Bookmark?

    suspend fun findByTitle(title: String): Bookmark?

    suspend fun findByExpiration(date: Date): List<Bookmark>

    suspend fun findByExpiration(start: Date, end: Date): List<Bookmark>

    suspend fun upsert(bookmark: Bookmark)

    suspend fun remove(bookmark: Bookmark)
    suspend fun remove(id: String)

    suspend fun empty()
}
