package dev.bmcreations.shredder.home

import androidx.compose.*
import androidx.ui.core.Modifier
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.layout.padding
import androidx.ui.material.*
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Add
import androidx.ui.unit.dp
import dev.bmcreations.shredder.home.ui.edit.*
import dev.bmcreations.shredder.home.ui.list.BookmarkList
import dev.bmcreations.shredder.models.Bookmark
import dev.bmcreations.shredder.ui.navigation.Screen
import kotlin.reflect.KFunction1
import kotlin.reflect.KFunction2

typealias EditCall = KFunction2<String?, (Result<Bookmark>) -> Unit, Unit>
typealias UpsertBookmark = KFunction1<Bookmark, Unit>

@Composable
fun HomeScreen(
        navigateTo: (Screen) -> Unit,
        onEdit: EditCall,
        bookmarks: List<Bookmark> = remember { emptyList() },
        scaffoldState: ScaffoldState = remember { ScaffoldState() },
        upsert: UpsertBookmark
) {
    val sheet = remember { mutableStateOf(BottomDrawerState.Closed) }
    val (sheetState, onStateChange) = sheet
    val editState = remember<MutableState<EditRequest>> { mutableStateOf(EditRequest.Nothing) }

    if (sheet.value == BottomDrawerState.Closed) {
        editState.value = EditRequest.Nothing
    }

    DynamicGesturesBottomSheet(
            sheetState,
            onStateChange,
            gesturesEnabled = sheetState != BottomDrawerState.Closed,
            drawerContent = {
                EditDialog(
                        request = editState.value,
                        onLoad = {
                            if (sheet.value == BottomDrawerState.Closed) {
                                sheet.value = BottomDrawerState.Opened
                            }
                        },
                        save = {
                            sheet.value = BottomDrawerState.Closed
                            upsert(it)
                        }
                )
            },
            bodyContent = {
                BodyContent(
                        scaffoldState,
                        bookmarks,
                        edit = { editState.value = EditRequest.Edit(it, onEdit) },
                        add = { editState.value = EditRequest.New }
                )
            }
    )
}

@Composable
private fun DynamicGesturesBottomSheet(
        drawerState: BottomDrawerState,
        onStateChange: (BottomDrawerState) -> Unit,
        gesturesEnabled: Boolean,
        drawerContent: @Composable () -> Unit,
        bodyContent: @Composable () -> Unit) {
    BottomDrawerLayout(
            drawerState = drawerState,
            onStateChange = onStateChange,
            drawerShape = RoundedCornerShape(topLeft = 32.dp, topRight = 32.dp),
            gesturesEnabled = gesturesEnabled,
            drawerContent = drawerContent,
            bodyContent = bodyContent
    )
}

@Composable
private fun BodyContent(
        scaffoldState: ScaffoldState,
        bookmarks: List<Bookmark>,
        edit: (Bookmark) -> Unit,
        add: () -> Unit
) {
    Scaffold(
            scaffoldState = scaffoldState,
            topBar = { TopBar() },
            bodyContent = { innerPadding ->
                BookmarkList(Modifier.padding(innerPadding), bookmarks, edit)
            },
            bottomBar = { BottomBar() },
            isFloatingActionButtonDocked = true,
            floatingActionButtonPosition = Scaffold.FabPosition.Center,
            floatingActionButton = { Fab { add() } }
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