package dev.bmcreations.shredder.home.ui

import androidx.annotation.MainThread
import androidx.compose.Composable
import androidx.compose.collectAsState
import androidx.lifecycle.ViewModel
import dev.bmcreations.shredder.home.data.usecases.GetBookmarks
import dev.bmcreations.shredder.home.data.usecases.LoadBookmark
import dev.bmcreations.shredder.home.data.usecases.UpsertBookmark
import dev.bmcreations.shredder.models.Bookmark

class BookmarkViewModel private constructor(
    private val loadById: LoadBookmark,
    private val getBookmarks: GetBookmarks,
    private val upsertBookmark: UpsertBookmark,
): ViewModel() {

    fun loadAll() = getBookmarks()

    fun loadBookmark(id: String? = null, callback: (Result<Bookmark>) -> Unit) {
        loadById(id, callback)
    }

    fun upsert(upsert: Bookmark) {
        upsertBookmark(upsert)
    }

    companion object {
        fun create(
            loadBookmark: LoadBookmark,
            getBookmarks: GetBookmarks,
            upsertBookmark: UpsertBookmark
        ): BookmarkViewModel = BookmarkViewModel(loadBookmark, getBookmarks, upsertBookmark)
    }
}


