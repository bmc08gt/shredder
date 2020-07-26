package dev.bmcreations.shredder.home

import androidx.compose.Composable
import androidx.compose.MutableState
import androidx.compose.remember
import androidx.compose.state
import androidx.ui.core.Modifier
import androidx.ui.foundation.*
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Add
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import dev.bmcreations.shredder.ui.navigation.Screen
import dev.bmcreations.shredder.home.edit.EditDialog
import dev.bmcreations.shredder.ui.utils.ThemedPreview

@Composable
fun HomeScreen(
    navigateTo: (Screen) -> Unit,
    scaffoldState: ScaffoldState = remember { ScaffoldState() }
) {
    val state = state { BottomDrawerState.Closed }
    val onStateChange = state.component2()
    BottomDrawerLayout(
            drawerState = state.component1(),
            onStateChange = state.component2(),
            drawerShape = RoundedCornerShape(topLeft = 32.dp, topRight = 32.dp),
            gesturesEnabled = true,
            drawerContent = { EditDialog(scaffoldState) },
            bodyContent = { BodyContent(scaffoldState, state) }
    )
}

@Composable
private fun TopBar(scaffoldState: ScaffoldState) {
    TopAppBar(
            title = { Text(text = "shredder") },
            backgroundColor = MaterialTheme.colors.secondary,
            contentColor = MaterialTheme.colors.onSecondary
    )
}

@Composable
private fun BodyContent(scaffoldState: ScaffoldState, state: MutableState<BottomDrawerState>) {
    Scaffold(
            scaffoldState = scaffoldState,
            topBar = { TopBar(scaffoldState) },
            bodyContent = { innerPadding -> Content(scaffoldState, Modifier.padding(innerPadding)) },
            bottomBar = { BottomBar(scaffoldState) },
            isFloatingActionButtonDocked = true,
            floatingActionButtonPosition = Scaffold.FabPosition.Center,
            floatingActionButton = { Fab { state.value = BottomDrawerState.Opened } }
    )
}

@Composable
private fun Content(scaffoldState: ScaffoldState, padding: Modifier) {

}

@Composable
private fun BottomBar(scaffoldState: ScaffoldState) {
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

@Preview("Home screen body")
@Composable
fun PreviewHomeScreenBody() {
    ThemedPreview {
        HomeScreen(
                navigateTo = {}
        )
    }
}
