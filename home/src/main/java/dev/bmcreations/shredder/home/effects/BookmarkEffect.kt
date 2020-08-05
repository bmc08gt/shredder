package dev.bmcreations.shredder.home.effects

import androidx.compose.*
import dev.bmcreations.shredder.bookmarks.Library
import dev.bmcreations.shredder.models.Bookmark
import dev.bmcreations.shredder.ui.state.UiState

@Composable
fun fetchBookmark(bookmarkId: String?, library: Library): UiState<Bookmark> {

    var fetchState: UiState<Bookmark> by state { UiState.Loading }

    library.findById(bookmarkId) { result ->
        fetchState = when {
            result.isSuccess -> {
                val data = result.getOrNull()
                if (data != null) {
                    UiState.Success(data)
                } else {
                    UiState.Error(Exception("bookmark with ID doesn't exist"))
                }
            }
            result.isFailure -> UiState.Error(result.exceptionOrNull())
            else -> fetchState
        }
    }

    return fetchState
}