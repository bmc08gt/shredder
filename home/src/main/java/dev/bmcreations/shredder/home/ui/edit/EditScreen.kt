package dev.bmcreations.shredder.home.ui.edit

import androidx.compose.Composable
import androidx.compose.mutableStateOf
import androidx.compose.remember
import androidx.compose.state
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.input.TextFieldValue
import androidx.ui.layout.*
import androidx.ui.material.ExtendedFloatingActionButton
import androidx.ui.material.MaterialTheme
import androidx.ui.material.OutlinedTextField
import androidx.ui.material.Surface
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Save
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontFamily
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import dev.bmcreations.shredder.home.EditCall
import dev.bmcreations.shredder.home.UpsertBookmark
import dev.bmcreations.shredder.home.effects.fetchBookmark
import dev.bmcreations.shredder.models.Bookmark
import dev.bmcreations.shredder.ui.state.UiState

sealed class EditRequest {
    object New : EditRequest()
    data class Edit(val bookmark: Bookmark, val requestCall: EditCall) : EditRequest()
    object Nothing : EditRequest()
}

@OptIn(ExperimentalLayout::class)
@Composable
fun EditDialog(
        request: EditRequest,
        onLoad: () -> Unit,
        save: (Bookmark) -> Unit
) {
    when (request) {
        EditRequest.New -> EditDialog(EditModel(label = "", url = ""), onLoad, save)
        is EditRequest.Edit -> {
            val bookmarkState = with(request) { fetchBookmark(bookmark.id, requestCall) }
            if (bookmarkState is UiState.Success<Bookmark>) {
                EditDialog(bookmarkState.data.edit(), onLoad, save)
            } else if (bookmarkState is UiState.Error) {
                EditDialog(EditModel(label = "", url = ""), onLoad, save)
            }
        }
        EditRequest.Nothing -> { }
    }
}

@Composable
private fun EditDialog(
        bookmark: EditModel,
        onLoad: () -> Unit,
        save: (Bookmark) -> Unit
) {
    val inFlight = remember { mutableStateOf(EditModel(label = "", url = "").let { bookmark }) }

    Stack {
        Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.secondary
        ) {
            Box(shape = RoundedCornerShape(topLeft = 32.dp, topRight = 32.dp)) {
                Text(
                        modifier = Modifier.padding(16.dp) + Modifier.padding(start = 8.dp),
                        style = TextStyle(fontSize = 16.sp, fontFamily = FontFamily.Monospace),
                        text = if (bookmark.id == null) "Create new bookmark" else "Edit"
                )
            }
        }
        Surface(
                modifier = Modifier.fillMaxSize() + Modifier.padding(top = 48.dp, start = 4.dp, end = 4.dp),
                color = MaterialTheme.colors.surface,
                shape = RoundedCornerShape(topLeft = 32.dp, topRight = 32.dp)
        ) {
            ConstraintLayout(modifier = Modifier.padding(16.dp)) {
                val (label, url, submit) = createRefs()

                EntryTextField(
                        modifier = Modifier.constrainAs(label) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                        },
                        defaultString = "Label",
                        bookmark.label
                ) {
                    inFlight.value = inFlight.value.copy(label = it)
                }
                EntryTextField(
                        modifier = Modifier.constrainAs(url) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(label.bottom)
                        },
                        defaultString = "URL",
                        bookmark.url
                ) {
                    inFlight.value = inFlight.value.copy(url = it)
                }

                ExtendedFloatingActionButton(
                        modifier = Modifier.constrainAs(submit) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(url.bottom, margin = 2.dp)
                        } + Modifier.padding(16.dp),
                        text = { Text("Save") },
                        icon = { Icon(Icons.Filled.Save) },
                        backgroundColor = MaterialTheme.colors.onSurface,
                        contentColor = MaterialTheme.colors.surface,
                        onClick = {
                            if (inFlight.value.isValid()) {
                                save(inFlight.value.asBookmark())
                            }
                        })
            }
        }
    }
    onLoad()
}

@Composable
private fun EntryTextField(
        modifier: Modifier = Modifier,
        defaultString: String,
        value: String? = null,
        onValueChange: (String) -> Unit
) {
    Surface(modifier = modifier + Modifier.padding(start = 8.dp, end = 8.dp)) {
        val textValue = state { TextFieldValue(value ?: defaultString) }
        OutlinedTextField(
                value = textValue.value,
                modifier = Modifier.padding(8.dp) + Modifier.fillMaxWidth(),
                // Update value of textValue with the latest value of the text field
                onValueChange = { text ->
                    textValue.value = text
                    onValueChange(text.text)
                },
                label = { Text(defaultString) }
        )
    }
}
