package dev.bmcreations.shredder.ui.theme

import androidx.compose.Composable
import androidx.ui.foundation.isSystemInDarkTheme
import androidx.ui.material.MaterialTheme
import androidx.ui.material.darkColorPalette
import androidx.ui.material.lightColorPalette


private val DarkColorPalette = darkColorPalette(
    primary = colorPrimary,
    secondary = colorSecondary,
    onSurface = colorOnSurface,
)

private val LightColorPalette = lightColorPalette(
    primary = colorPrimary,
    secondary = colorSecondary,
    onSurface = colorOnSurface

        /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun ShredderTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
            colors = colors,
            typography = typography,
            shapes = shapes,
            content = content
    )
}
