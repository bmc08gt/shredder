package dev.bmcreations.shredder.home.ui

import androidx.compose.Composable
import androidx.compose.MutableState
import androidx.compose.mutableStateOf
import androidx.compose.remember
import androidx.ui.material.BottomDrawerState
import androidx.ui.material.ScaffoldState
import dev.bmcreations.shredder.home.ui.edit.EditRequest
import dev.bmcreations.shredder.models.Bookmark
import dev.bmcreations.shredder.ui.navigation.Screen
import dev.bmcreations.shredder.ui.sheet.DynamicGesturesBottomSheet
import dev.bmcreations.shredder.ui.snack.SnackbarState
import kotlin.reflect.KFunction1
import kotlin.reflect.KFunction2

typealias LoadById = KFunction2<String?, (Result<Bookmark>) -> Unit, Unit>
typealias UpdateBookmark = KFunction1<Bookmark, Boolean>
typealias DeleteBookmark = KFunction1<Bookmark, Boolean>

@Composable
fun HomeScreen(
    navigateTo: (Screen) -> Unit,
    loadBookmark: LoadById,
    bookmarks: List<Bookmark> = remember { emptyList() },
    scaffoldState: ScaffoldState = remember { ScaffoldState() },
    upsert: UpdateBookmark,
    delete: DeleteBookmark
) {
    val sheetState = remember { mutableStateOf(BottomDrawerState.Closed) }
    val editState = remember<MutableState<EditRequest>> { mutableStateOf(EditRequest.Nothing) }
    val snackbarState = remember { SnackbarState() }

    when (val edit = editState.value) {
        is EditRequest.Delete -> delete(edit.bookmark)
        is EditRequest.UndoDelete -> upsert(edit.bookmark)
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
        drawerContent = { DrawerContent(editState, sheetState, upsert) },
        bodyContent = { BodyContent(scaffoldState, snackbarState, editState, bookmarks, loadBookmark, delete, upsert) }
    )
}