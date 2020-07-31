package dev.bmcreations.shredder.home.di

import dev.bmcreations.shredder.core.di.AppComponent
import dev.bmcreations.shredder.core.di.Component
import dev.bmcreations.shredder.home.data.repository.FakeBookmarkRepositoryImpl
import dev.bmcreations.shredder.home.data.usecases.DeleteBookmark
import dev.bmcreations.shredder.home.data.usecases.GetBookmarks
import dev.bmcreations.shredder.home.data.usecases.LoadBookmark
import dev.bmcreations.shredder.home.data.usecases.UpsertBookmark
import dev.bmcreations.shredder.home.ui.BookmarkViewModel

/**
 * Dependency Injection container at the application level.
 */
interface HomeComponent : Component {
    val bookmarkViewModel: BookmarkViewModel
}

class HomeComponentImpl(appComponent: AppComponent) : HomeComponent {
    private val repository = FakeBookmarkRepositoryImpl()

    override val bookmarkViewModel: BookmarkViewModel = BookmarkViewModel.create(
            loadBookmark = LoadBookmark(repository),
            getBookmarks = GetBookmarks(repository),
            upsertBookmark = UpsertBookmark(repository),
            deleteBookmark = DeleteBookmark(repository)
    )
}
