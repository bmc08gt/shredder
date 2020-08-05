package dev.bmcreations.shredder.bookmarks.dao

import dev.bmcreations.shredder.models.Bookmark

/**
 * All actions to access and modify our [Bookmark] database.
 */
interface BookmarksDao {

    suspend fun selectAll(): List<Bookmark>

    suspend fun lastCreatedBookmark(): Bookmark?

    fun findById(id: String, callback: (Result<Bookmark>) -> Unit)
    fun findByUrl(url: String, callback: (Result<Bookmark>) -> Unit)
    fun findByLabel(label: String, callback: (Result<Bookmark>) -> Unit)

    suspend fun upsert(vararg bookmark: Bookmark)

    suspend fun remove(vararg bookmark: Bookmark)
    suspend fun remove(id: String)

    suspend fun empty()
}