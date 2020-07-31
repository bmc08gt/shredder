package dev.bmcreations.shredder.ui.gestures

import androidx.compose.Composable
import androidx.compose.mutableStateOf
import androidx.compose.remember
import androidx.ui.core.Constraints
import androidx.ui.core.Modifier
import androidx.ui.core.gesture.scrollorientationlocking.Orientation
import androidx.ui.foundation.gestures.draggable
import androidx.ui.layout.offsetPx
import androidx.ui.unit.dp


@Composable
fun Modifier.swipeToDelete(
    constraints: Constraints,
    threshold: Float = .7f,
    onSwipe: () -> Unit
): Modifier {
    val width = constraints.maxWidth
    val max = width.dp
    val min = 0.dp
    // this is the  state we will update while dragging
    val offsetPosition = remember { mutableStateOf(0f) }
    val draggable = remember { mutableStateOf(true) }

    return this + Modifier.draggable(
        enabled = draggable.value,
        orientation = Orientation.Horizontal,
        onDragStopped = {
            offsetPosition.value = 0f
            draggable.value = true
        }
    ) { delta ->
        val newValue = offsetPosition.value + delta
        offsetPosition.value = newValue.coerceIn(min.toPx(), max.toPx())

        if (offsetPosition.value >= width * threshold) {
            onSwipe()
            draggable.value = false
        }
    } + Modifier.offsetPx(x = offsetPosition)
}