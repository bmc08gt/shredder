package dev.bmcreations.shredder.home.ui

import androidx.compose.Composable
import androidx.compose.MutableState
import androidx.compose.mutableStateOf
import androidx.compose.remember
import androidx.ui.material.BottomDrawerState
import androidx.ui.material.ScaffoldState
import dev.bmcreations.shredder.bookmarks.Library
import dev.bmcreations.shredder.home.ui.state.EditRequest
import dev.bmcreations.shredder.ui.navigation.Screen
import dev.bmcreations.shredder.ui.sheet.DynamicGesturesBottomSheet
import dev.bmcreations.shredder.ui.snack.SnackbarState

@Composable
fun HomeScreen(
    scaffoldState: ScaffoldState = remember { ScaffoldState() },
    navigateTo: (Screen) -> Unit,
    library: Library
) {
    val sheetState = remember { mutableStateOf(BottomDrawerState.Closed) }
    val editState = remember<MutableState<EditRequest>> { mutableStateOf(EditRequest.Nothing) }
    val snackbarState = remember { SnackbarState() }

    when (val edit = editState.value) {
        is EditRequest.Delete -> library.remove(edit.bookmark)
        is EditRequest.UndoDelete -> library.upsert(edit.bookmark)
    }

    DynamicGesturesBottomSheet(
        sheetState.value,
        onStateChange = {
            // Reset our edit state explicitly when the sheet is closed
            if (it == BottomDrawerState.Closed) {
                editState.value = EditRequest.Nothing
            }
        },
        gesturesEnabled = sheetState.value != BottomDrawerState.Closed,
        drawerContent = { DrawerContent(editState, sheetState, library) },
        bodyContent = { BodyContent(scaffoldState, snackbarState, editState, library) }
    )
}