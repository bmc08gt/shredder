package dev.bmcreations.shredder.home.ui

import androidx.compose.Composable
import androidx.compose.MutableState
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.layout.Stack
import androidx.ui.layout.padding
import androidx.ui.material.*
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Add
import androidx.ui.unit.dp
import dev.bmcreations.shredder.home.ui.edit.EditRequest
import dev.bmcreations.shredder.models.Bookmark
import dev.bmcreations.shredder.ui.snack.TransientSnackbar
import dev.bmcreations.shredder.ui.snack.SnackbarState

@Composable
internal fun BodyContent(
    scaffoldState: ScaffoldState,
    snackbarState: SnackbarState,
    editState: MutableState<EditRequest>,
    bookmarks: List<Bookmark>,
    loadById: LoadById,
    delete: DeleteBookmark,
    undo: UpdateBookmark
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopBar() },
        bodyContent = { innerPadding ->
            Stack(modifier = Modifier.padding(top = 8.dp)) {
                BookmarkList(
                    modifier = Modifier.padding(innerPadding),
                    bookmarks = bookmarks,
                    onEdit = { editState.value = EditRequest.Edit(this, loadById) },
                    onDelete = {
                        editState.value = EditRequest.Delete(this, delete)
                        snackbarState.show = true
                    }
                )

                TransientSnackbar(
                    modifier = Modifier.gravity(Alignment.BottomCenter) + Modifier.padding(bottom = 72.dp, start = 16.dp, end = 16.dp),
                    snackbarState = snackbarState,
                    text = "Bookmark deleted.",
                    actionLabel = "Undo",
                    onAction = {
                        val bookmark = when (val state = editState.value) {
                            is EditRequest.Delete -> state.bookmark
                            else -> null
                        }
                        bookmark?.let {
                            editState.value = EditRequest.UndoDelete(it, undo)
                        }
                    },
                    onDismiss = { editState.value = EditRequest.Nothing }
                )
            }
        },
        bottomBar = { BottomBar() },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = Scaffold.FabPosition.Center,
        floatingActionButton = { Fab { editState.value = EditRequest.New } }
    )
}

@Composable
private fun TopBar() {
    TopAppBar(
        title = { Text(text = "shredder") },
        backgroundColor = MaterialTheme.colors.secondary,
        contentColor = MaterialTheme.colors.onSecondary
    )
}

@Composable
private fun BottomBar() {
    BottomAppBar(
        cutoutShape = CircleShape,
        backgroundColor = MaterialTheme.colors.surface,
        content = {}
    )
}

@Composable
private fun Fab(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        backgroundColor = MaterialTheme.colors.secondary,
        shape = CircleShape,
    ) {
        Icon(Icons.Filled.Add, tint = MaterialTheme.colors.onSecondary)
    }
}