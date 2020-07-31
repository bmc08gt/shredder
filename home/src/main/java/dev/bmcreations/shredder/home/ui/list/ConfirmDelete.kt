package dev.bmcreations.shredder.home.ui.list

import androidx.compose.Composable
import dev.bmcreations.shredder.models.Bookmark
import dev.bmcreations.shredder.ui.dialogs.ThemedConfirmationDialog


sealed class DeleteRequest {
    data class Triggered(val bookmark: Bookmark) : DeleteRequest()
    object Nothing : DeleteRequest()
}

@Composable
fun ConfirmDelete(
    bookmark: Bookmark,
    onCloseRequest: () -> Unit,
    onConfirm: (Bookmark) -> Unit
) {
    ThemedConfirmationDialog(
        title = "Delete",
        text = "Are you sure you want to delete this bookmark?",
        onConfirm = { onConfirm(bookmark) },
        onCloseRequest = onCloseRequest,
    )
}