package dev.bmcreations.expiry.bar

import dev.bmcreations.expiry.models.Bookmark
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookmarksBar(
    private val dao: BookmarksDao
): CoroutineScope by CoroutineScope(Dispatchers.IO) {

    fun bookmarks(): List<Bookmark> {
        val results = mutableListOf<Bookmark>()
        launch {
            results.addAll(dao.selectAll())
        }
        return results
    }
}
