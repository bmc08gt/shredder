package dev.bmcreations.shredder.bookmarks

import dev.bmcreations.shredder.bookmarks.usecases.*
import dev.bmcreations.shredder.models.Bookmark
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.*

class Library(
    private val getBookmarks: GetBookmarks,
    private val getBookmarkById: GetBookmarkById,
    private val getBookmarkByLabel: GetBookmarkByLabel,
    private val getBookmarkByUrl: GetBookmarkByUrl,
    private val upsertBookmark: UpsertBookmark,
    private val deleteBookmark: DeleteBookmark,
    private val clearBookmarks: ClearBookmarks
): CoroutineScope by CoroutineScope(Dispatchers.IO) {

    // get
    suspend fun bookmarks(): List<Bookmark> {
        return getBookmarks()
    }

    fun observeBookmarks(): Flow<List<Bookmark>> {
        return flow {
            emit(bookmarks())
        }
    }

    suspend fun getLastBookmark(): Bookmark? {
        return getBookmarks().minByOrNull { it.createdAt }
    }


    fun findById(id: String?, callback: (Result<Bookmark>) -> Unit) {
        getBookmarkById(id, callback)
    }

    fun findByUrl(url: String?, callback: (Result<Bookmark>) -> Unit) {
        getBookmarkByUrl(url, callback)
    }

    fun findByLabel(label: String?, callback: (Result<Bookmark>) -> Unit) {
        getBookmarkByLabel(label, callback)
    }

    //endregion

    //put
    fun upsert(vararg bookmark: Bookmark) {
        launch(Dispatchers.IO) {
            bookmark.forEach {
                upsertBookmark(it)
            }
        }
    }
    //endregion

    //remove
    fun remove(vararg bookmark: Bookmark) {
        launch(Dispatchers.IO) {
            bookmark.forEach {
                deleteBookmark(it)
            }
        }
    }


    fun empty() {
        launch {
            clearBookmarks()
        }
    }
}