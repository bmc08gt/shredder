package dev.bmcreations.shredder.ui.utils

import androidx.compose.Composable
import androidx.ui.material.Surface
import dev.bmcreations.shredder.ui.theme.ShredderTheme

@Composable
internal fun ThemedPreview(
    darkTheme: Boolean = false,
    children: @Composable() () -> Unit
) {
    ShredderTheme(darkTheme = darkTheme) {
        Surface {
            children()
        }
    }
}
