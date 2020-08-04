package dev.bmcreations.shredder.home.ui

import androidx.compose.Composable
import androidx.compose.MutableState
import androidx.ui.material.BottomDrawerState
import dev.bmcreations.shredder.home.ui.edit.EditDialog
import dev.bmcreations.shredder.home.ui.edit.EditRequest

@Composable
internal fun DrawerContent(
    editState: MutableState<EditRequest>,
    sheetState: MutableState<BottomDrawerState>,
    upsert: UpdateBookmark
) {
    EditDialog(
        request = editState,
        sheetState = sheetState,
        save = {
            upsert(it)
            editState.value = EditRequest.Nothing
            sheetState.value = BottomDrawerState.Closed
        }
    )
}