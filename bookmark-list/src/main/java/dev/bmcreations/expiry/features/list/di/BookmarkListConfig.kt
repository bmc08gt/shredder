package dev.bmcreations.expiry.features.list.di

import dev.bmcreations.expiry.bar.BookmarksBar
import dev.bmcreations.expiry.bar.BookmarksComponent
import dev.bmcreations.expiry.core.di.Component
import dev.bmcreations.expiry.core.di.component
import dev.bmcreations.expiry.di.NetworkComponent
import dev.bmcreations.expiry.features.list.BookmarkListViewModel
import dev.bmcreations.expiry.network.repository.WebManifestRepository

interface BookmarkListComponent: Component {
    val bookmarks: BookmarksComponent
    val viewModel: BookmarkListViewModel
    val manifestFetcher: WebManifestRepository
}

class BookmarkListComponentImpl(override val bookmarks: BookmarksComponent): BookmarkListComponent {
    override val manifestFetcher: WebManifestRepository
        get() = "webManifest".component<NetworkComponent>().manifestFetcher

    override val viewModel: BookmarkListViewModel = BookmarkListViewModel.create(manifestFetcher)
}
