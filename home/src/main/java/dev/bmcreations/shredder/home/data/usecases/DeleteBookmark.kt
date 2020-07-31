package dev.bmcreations.shredder.home.data.usecases

import dev.bmcreations.shredder.home.data.repository.BookmarkRepository
import dev.bmcreations.shredder.models.Bookmark

class DeleteBookmark(
        private val repository: BookmarkRepository
) {
    operator fun invoke(bookmark: Bookmark): Boolean = repository.removeBookmark(bookmark)
}