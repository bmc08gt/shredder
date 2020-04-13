package dev.bmcreations.shredder.features.list.di

import dev.bmcreations.shredder.bar.BookmarksComponent
import dev.bmcreations.shredder.core.di.Component
import dev.bmcreations.shredder.core.di.Components
import dev.bmcreations.shredder.core.di.component
import dev.bmcreations.shredder.di.NetworkComponent
import dev.bmcreations.shredder.features.list.view.BookmarkListViewModel
import dev.bmcreations.shredder.network.repository.WebManifestRepository

interface BookmarkListComponent: Component {
    val bookmarks: BookmarksComponent
    val viewModel: BookmarkListViewModel
    val manifestFetcher: WebManifestRepository
}

class BookmarkListComponentImpl(override val bookmarks: BookmarksComponent): BookmarkListComponent {
    override val manifestFetcher: WebManifestRepository
        get() = Components.WEB_MANIFEST.component<NetworkComponent>().manifestFetcher

    override val viewModel: BookmarkListViewModel = BookmarkListViewModel.create(manifestFetcher)
}
