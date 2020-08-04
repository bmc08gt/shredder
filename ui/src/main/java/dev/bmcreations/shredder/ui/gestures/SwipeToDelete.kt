package dev.bmcreations.shredder.ui.gestures

import androidx.animation.IntToVectorConverter
import androidx.animation.tween
import androidx.compose.*
import androidx.ui.animation.animatedFloat
import androidx.ui.animation.animatedValue
import androidx.ui.core.*
import androidx.ui.core.gesture.scrollorientationlocking.Orientation
import androidx.ui.foundation.animation.FlingConfig
import androidx.ui.foundation.animation.fling
import androidx.ui.foundation.gestures.draggable
import kotlinx.coroutines.*
import kotlin.math.absoluteValue
import kotlin.math.roundToInt


/**
 * [Modifier] enabling swipe to delete functionality on a Composable view.
 *
 * NOTE: <b>MUST</b> be added prior to padding modifiers to ensure that the collapse animation
 * will run to remove all available space.
 */
@Composable
fun Modifier.swipeToDelete(
    constraints: Constraints,
    threshold: Float = .8f,
    swipeDirection: LayoutDirection? = null,
    onDelete: () -> Unit
): Modifier {
    val width = constraints.maxWidth

    val draggable = remember { mutableStateOf(true) }
    val deleted = remember { mutableStateOf(false) }

    val positionOffset = animatedFloat(0f)
    val collapse = remember { mutableStateOf( 0) }
    val animatedCollapse = animatedValue(initVal = 0, converter = IntToVectorConverter)


    return this + Modifier.draggable(
        enabled = draggable.value,
        orientation = Orientation.Horizontal,
        onDrag = { delta ->
            when (swipeDirection) {
                LayoutDirection.Ltr -> positionOffset.snapTo((positionOffset.value + delta).coerceAtLeast(0f))
                LayoutDirection.Rtl -> positionOffset.snapTo((positionOffset.value + delta).coerceAtMost(0f))
                else -> positionOffset.snapTo(positionOffset.value + delta)
            }
        },
        onDragStopped = { velocity ->
            val config = FlingConfig(anchors = listOf(-width.toFloat(), 0f, width.toFloat()))
            if (positionOffset.value.absoluteValue >= threshold) {
                positionOffset.fling(velocity, config) { _, endValue, _ ->
                    if (endValue != 0f) {
                        animatedCollapse.snapTo(collapse.value)
                        animatedCollapse.animateTo(0, onEnd = { _, _ ->
                            deleted.value = true
                            draggable.value = false
                            onDelete()
                        }, anim = tween(500))
                    }
                }
            } else {
                draggable.value = true
            }
        }
    ) + object : LayoutModifier {
        override fun MeasureScope.measure(
            measurable: Measurable,
            constraints: Constraints,
            layoutDirection: LayoutDirection
        ): MeasureScope.MeasureResult {
            val child = measurable.measure(constraints)
            positionOffset.setBounds(-width.toFloat(), width.toFloat())

            collapse.value = child.height

            val placeHeight = if (animatedCollapse.isRunning || deleted.value) {
                animatedCollapse.value
            } else {
                child.height
            }
            return layout(child.width, placeHeight) {
                child.place(positionOffset.value.roundToInt(), 0)
            }
        }
    }
}