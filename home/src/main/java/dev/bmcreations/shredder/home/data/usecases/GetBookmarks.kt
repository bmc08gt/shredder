package dev.bmcreations.shredder.home.data.usecases

import androidx.compose.Composable
import androidx.compose.State
import androidx.compose.collectAsState
import dev.bmcreations.shredder.home.data.repository.BookmarkRepository
import dev.bmcreations.shredder.models.Bookmark
import kotlinx.coroutines.flow.Flow

class GetBookmarks(
    private val repository: BookmarkRepository
) {

    operator fun invoke(): Flow<List<Bookmark>> {
        return repository.loadBookmarks()
    }
}
