package dev.bmcreations.shredder.home.ui

import androidx.lifecycle.ViewModel
import dev.bmcreations.shredder.home.data.usecases.DeleteBookmark
import dev.bmcreations.shredder.home.data.usecases.GetBookmarks
import dev.bmcreations.shredder.home.data.usecases.LoadBookmark
import dev.bmcreations.shredder.home.data.usecases.UpsertBookmark
import dev.bmcreations.shredder.models.Bookmark

class BookmarkViewModel private constructor(
    private val loadById: LoadBookmark,
    private val getBookmarks: GetBookmarks,
    private val upsertBookmark: UpsertBookmark,
    private val deleteBookmark: DeleteBookmark
) : ViewModel() {

    fun loadAll() = getBookmarks()

    fun loadBookmark(id: String? = null, callback: (Result<Bookmark>) -> Unit) {
        loadById(id, callback)
    }

    fun upsert(upsert: Bookmark): Boolean {
        return upsertBookmark(upsert)
    }

    fun remove(removal: Bookmark): Boolean {
        return deleteBookmark(removal)
    }

    companion object {
        fun create(
            loadBookmark: LoadBookmark,
            getBookmarks: GetBookmarks,
            upsertBookmark: UpsertBookmark,
            deleteBookmark: DeleteBookmark
        ): BookmarkViewModel =
            BookmarkViewModel(loadBookmark, getBookmarks, upsertBookmark, deleteBookmark)
    }
}


