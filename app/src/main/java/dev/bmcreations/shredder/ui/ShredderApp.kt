package dev.bmcreations.shredder.ui

import androidx.compose.Composable
import androidx.ui.animation.Crossfade
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import dev.bmcreations.shredder.core.AppComponent
import dev.bmcreations.shredder.home.HomeScreen
import dev.bmcreations.shredder.ui.navigation.NavigationViewModel
import dev.bmcreations.shredder.ui.navigation.Screen
import dev.bmcreations.shredder.ui.theme.ShredderTheme

@Composable
fun ShredderApp(
        appComponent: AppComponent,
        navigationViewModel: NavigationViewModel
) {
    ShredderTheme {
        AppContent(
                navigationViewModel = navigationViewModel
        )
    }
}

@Composable
private fun AppContent(
        navigationViewModel: NavigationViewModel
) {
    Surface(color = MaterialTheme.colors.background) {
        when (val screen = navigationViewModel.currentScreen) {
            Screen.Home -> {
                Crossfade(screen) {
                    HomeScreen(navigateTo = navigationViewModel::navigateTo)
                }
            }
        }
    }
}
