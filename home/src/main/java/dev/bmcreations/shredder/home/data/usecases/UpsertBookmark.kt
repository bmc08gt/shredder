package dev.bmcreations.shredder.home.data.usecases

import dev.bmcreations.shredder.home.data.repository.BookmarkRepository
import dev.bmcreations.shredder.models.Bookmark

class UpsertBookmark(
        private val repository: BookmarkRepository
) {
    operator fun invoke(upsert: Bookmark) = repository.upsertBookmark(upsert)
}
