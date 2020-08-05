package dev.bmcreations.shredder.ui

import androidx.animation.AnimationSpec
import androidx.compose.Composable
import androidx.ui.animation.Crossfade
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import dev.bmcreations.shredder.bookmarks.Library
import dev.bmcreations.shredder.core.di.AppComponent
import dev.bmcreations.shredder.home.ui.HomeScreen
import dev.bmcreations.shredder.home.di.HomeComponent
import dev.bmcreations.shredder.ui.navigation.NavigationViewModel
import dev.bmcreations.shredder.ui.navigation.Screen
import dev.bmcreations.shredder.ui.theme.ShredderTheme

@Composable
fun ShredderApp(
    appComponent: AppComponent,
    homeComponent: HomeComponent,
    navigationViewModel: NavigationViewModel
) {
    ShredderTheme {
        AppContent(
            navigationViewModel = navigationViewModel,
            bookmarkLibrary = homeComponent.bookmarkLibrary
        )
    }
}

@Composable
private fun AppContent(
    navigationViewModel: NavigationViewModel,
    bookmarkLibrary: Library
) {
    Surface(color = MaterialTheme.colors.background) {
        when (val screen = navigationViewModel.currentScreen) {
            Screen.Home -> {
                Crossfade(screen) {
                    HomeScreen(
                        navigateTo = navigationViewModel::navigateTo,
                        library = bookmarkLibrary
                    )
                }
            }
        }
    }
}
