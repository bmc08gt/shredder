package dev.bmcreations.shredder.home.data.usecases

import dev.bmcreations.shredder.home.data.repository.BookmarkRepository
import dev.bmcreations.shredder.models.Bookmark
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetBookmarks(
    private val repository: BookmarkRepository
) {

    operator fun invoke(): Flow<List<Bookmark>> {
        return flow {
            val result = repository.loadBookmarks()
            emit(result.sortedByDescending { it.createdAt })
        }
    }
}
