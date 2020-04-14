package dev.bmcreations.shredder.features.list.di

import dev.bmcreations.shredder.bar.BookmarksComponent
import dev.bmcreations.shredder.core.di.Component
import dev.bmcreations.shredder.core.di.CoreComponent
import dev.bmcreations.shredder.di.NetworkComponent
import dev.bmcreations.shredder.environmentDimensionNetworkComponent
import dev.bmcreations.shredder.features.list.view.BookmarkListViewModel
import dev.bmcreations.shredder.network.manifest.repository.NetworkWebManifestRepositoryImpl
import dev.bmcreations.shredder.network.manifest.repository.WebManifestRepository
import dev.bmcreations.shredder.network.manifest.service.WebManifestService
import retrofit2.Retrofit

interface BookmarkListComponent : Component {
    val core: CoreComponent
    val bookmarks: BookmarksComponent
    val viewModel: BookmarkListViewModel
    val network: WebManifestNetworkComponent
}

abstract class WebManifestNetworkComponent : NetworkComponent {
    abstract val manifestFetcher: WebManifestRepository
}

class WebManifestNetworkComponentImpl(environment: NetworkComponent) :
    WebManifestNetworkComponent() {
    override val retrofit: Retrofit = environment.retrofit
    override val manifestFetcher: WebManifestRepository =
        NetworkWebManifestRepositoryImpl(retrofit.create(WebManifestService::class.java))
}

class BookmarkListComponentImpl(
    override val core: CoreComponent,
    override val bookmarks: BookmarksComponent
) : BookmarkListComponent {
    override val network: WebManifestNetworkComponent =
        WebManifestNetworkComponentImpl(core.app.environmentDimensionNetworkComponent())

    override val viewModel: BookmarkListViewModel = BookmarkListViewModel.create(network.manifestFetcher)
}
