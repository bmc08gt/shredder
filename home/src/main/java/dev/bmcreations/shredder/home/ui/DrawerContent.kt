package dev.bmcreations.shredder.home.ui

import androidx.compose.Composable
import androidx.compose.MutableState
import androidx.ui.material.BottomDrawerState
import dev.bmcreations.shredder.bookmarks.Library
import dev.bmcreations.shredder.home.ui.edit.EditDialog
import dev.bmcreations.shredder.home.ui.state.EditRequest

@Composable
internal fun DrawerContent(
    editState: MutableState<EditRequest>,
    sheetState: MutableState<BottomDrawerState>,
    library: Library
) {
    EditDialog(
        request = editState,
        sheetState = sheetState,
        save = {
            library.upsert(it)
            editState.value = EditRequest.Nothing
            sheetState.value = BottomDrawerState.Closed
        }
    )
}