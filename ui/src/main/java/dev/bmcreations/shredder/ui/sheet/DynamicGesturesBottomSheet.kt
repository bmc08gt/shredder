package dev.bmcreations.shredder.ui.sheet

import androidx.compose.Composable
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.material.BottomDrawerLayout
import androidx.ui.material.BottomDrawerState
import androidx.ui.unit.dp

@Composable
fun DynamicGesturesBottomSheet(
    drawerState: BottomDrawerState,
    onStateChange: (BottomDrawerState) -> Unit,
    gesturesEnabled: Boolean,
    drawerContent: @Composable () -> Unit,
    bodyContent: @Composable () -> Unit
) {
    BottomDrawerLayout(
        drawerState = drawerState,
        onStateChange = onStateChange,
        drawerShape = RoundedCornerShape(topLeft = 32.dp, topRight = 32.dp),
        gesturesEnabled = gesturesEnabled,
        drawerContent = drawerContent,
        bodyContent = bodyContent
    )
}