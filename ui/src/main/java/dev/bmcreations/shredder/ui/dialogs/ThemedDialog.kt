package dev.bmcreations.shredder.ui.dialogs

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.layout.ConstraintLayout
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.padding
import androidx.ui.material.AlertDialog
import androidx.ui.material.TextButton
import androidx.ui.unit.TextUnit
import androidx.ui.unit.dp

@Composable
fun ThemedConfirmationDialog(
    title: String,
    text: String,
    onConfirm: () -> Unit,
    onCloseRequest: () -> Unit,
) {
    AlertDialog(
        onCloseRequest = onCloseRequest,
        title = {
            Text(
                text = title,
                fontSize = TextUnit.Sp(24)
            )
        },
        text = {
            Text(
                text = text,
                fontSize = TextUnit.Sp(18)
            )
        },
        buttons = {
            ConstraintLayout(modifier = Modifier.fillMaxWidth() + Modifier.padding(bottom = 4.dp)) {
                val (cancel, confirm) = createRefs()
                TextButton(
                    modifier = Modifier.constrainAs(confirm) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    } + Modifier.padding(8.dp),
                    onClick = {
                        onConfirm()
                        onCloseRequest()
                    }
                ) {
                    Text(
                        text = "Yes",
                        fontSize = TextUnit.Sp(16)
                    )
                }
                TextButton(
                    modifier = Modifier.constrainAs(cancel) {
                        end.linkTo(confirm.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    } + Modifier.padding(8.dp),
                    onClick = onCloseRequest
                ) {
                    Text(
                        text = "No",
                        fontSize = TextUnit.Sp(16)
                    )
                }

            }
        }
    )
}