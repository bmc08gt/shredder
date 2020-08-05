package dev.bmcreations.shredder.home.ui.state

import dev.bmcreations.shredder.bookmarks.Library
import dev.bmcreations.shredder.models.Bookmark

sealed class EditRequest {
    object New : EditRequest()
    data class Edit(val bookmark: Bookmark, val library: Library) : EditRequest()
    data class Delete(val bookmark: Bookmark, val library: Library): EditRequest()
    data class UndoDelete(val bookmark: Bookmark, val library: Library): EditRequest()
    object Nothing : EditRequest()
}