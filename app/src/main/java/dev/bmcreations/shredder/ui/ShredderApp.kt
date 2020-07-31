package dev.bmcreations.shredder.ui

import androidx.compose.Composable
import androidx.compose.collectAsState
import androidx.ui.animation.Crossfade
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import dev.bmcreations.shredder.core.di.AppComponent
import dev.bmcreations.shredder.home.HomeScreen
import dev.bmcreations.shredder.home.di.HomeComponent
import dev.bmcreations.shredder.home.ui.BookmarkViewModel
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
            bookmarkViewModel = homeComponent.bookmarkViewModel
        )
    }
}

@Composable
private fun AppContent(
    navigationViewModel: NavigationViewModel,
    bookmarkViewModel: BookmarkViewModel
) {
    Surface(color = MaterialTheme.colors.background) {
        when (val screen = navigationViewModel.currentScreen) {
            Screen.Home -> {
                Crossfade(screen) {
                    val bookmarks =
                        bookmarkViewModel.loadAll().collectAsState(initial = emptyList())
                    HomeScreen(
                        navigateTo = navigationViewModel::navigateTo,
                        bookmarks = bookmarks.value,
                        onEdit = bookmarkViewModel::loadBookmark,
                        upsert = bookmarkViewModel::upsert,
                        delete = bookmarkViewModel::remove

                    )
                }
            }
        }
    }
}
