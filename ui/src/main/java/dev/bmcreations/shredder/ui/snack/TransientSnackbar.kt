package dev.bmcreations.shredder.ui.snack

import androidx.animation.IntToVectorConverter
import androidx.compose.*
import androidx.ui.animation.animatedValue
import androidx.ui.core.*
import androidx.ui.foundation.ClickableText
import androidx.ui.foundation.Text
import androidx.ui.graphics.Shape
import androidx.ui.layout.padding
import androidx.ui.material.*
import androidx.ui.text.AnnotatedString
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontWeight
import androidx.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt

class SnackbarState {
    var show by mutableStateOf(false)
}

@Composable
fun TransientSnackbar(
    modifier: Modifier = Modifier,
    snackbarState: SnackbarState,
    text: String,
    actionLabel: String,
    onAction: () -> Unit,
    shape: Shape = MaterialTheme.shapes.small,
    autoDismiss: Boolean = true,
    timeout: Long = 4_000,
    onDismiss: (SnackbarState) -> Unit
) {

    if(snackbarState.show) {
        Snackbar(
            modifier = modifier,
            text = { Text(text = text) },
            shape = shape,
            action = {
                ClickableText(
                    text = AnnotatedString(text = actionLabel),
                    modifier = Modifier.padding(16.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.secondary
                    ),
                    onClick = {
                        onAction()
                        snackbarState.show = false
                    }
                )
            }
        )
    }

    if (autoDismiss && snackbarState.show) {
        launchInComposition {
            delay(timeout)
            snackbarState.show = false
            onDismiss(snackbarState)
        }
    }
}
