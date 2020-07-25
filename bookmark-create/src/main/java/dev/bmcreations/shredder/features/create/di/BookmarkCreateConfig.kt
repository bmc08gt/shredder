package dev.bmcreations.shredder.features.create.di

import com.jakewharton.threetenabp.AndroidThreeTen
import dev.bmcreations.shredder.bar.BookmarksComponent
import dev.bmcreations.shredder.core.di.Component
import dev.bmcreations.shredder.core.di.CoreComponent
import dev.bmcreations.shredder.di.NetworkComponent
import dev.bmcreations.shredder.environmentDimensionNetworkComponent
import dev.bmcreations.shredder.features.create.repository.BookmarkCreateRepositoryImpl
import dev.bmcreations.shredder.features.create.usecases.*
import dev.bmcreations.shredder.features.create.view.BookmarkCreateViewModel
import dev.bmcreations.shredder.network.sync.repository.BookmarkSyncRepository
import dev.bmcreations.shredder.network.sync.repository.BookmarkSyncRepositoryImpl
import dev.bmcreations.shredder.network.sync.service.BookmarkSyncService
import retrofit2.Retrofit

interface BookmarkCreateComponent : Component {
    val bookmarks: BookmarksComponent
    val viewModel: BookmarkCreateViewModel
    val network: BookmarkSyncNetworkComponent
}

abstract class BookmarkSyncNetworkComponent : NetworkComponent {
    abstract val sync: BookmarkSyncRepository
}

class BookmarkSyncNetworkComponentImpl(environment: NetworkComponent) : BookmarkSyncNetworkComponent() {
    override val retrofit: Retrofit = environment.retrofit
    override val sync: BookmarkSyncRepository = BookmarkSyncRepositoryImpl(
        retrofit.create(BookmarkSyncService::class.java)
    )
}

class BookmarkCreateComponentImpl(
    coreComponent: CoreComponent,
    override val bookmarks: BookmarksComponent
) : BookmarkCreateComponent {

    init {
        // calendar view requires ThreeTenABP
        AndroidThreeTen.init(coreComponent.app)
    }

    private val repository = BookmarkCreateRepositoryImpl(bookmarks)

    override val viewModel = BookmarkCreateViewModel.create(
        loadBookmarkUseCase = LoadBookmarkUseCase(repository),
        getGroupsUseCase = GetGroupsLiveDataUseCase(repository),
        getSelectedGroupUseCase = GetSelectedGroupUseCase(repository),
        setSelectedGroupUseCase = SelectGroupUseCase(repository),
        getTitleUseCase = GetLabelUseCase(repository),
        setTitleUseCase = SetLabelUseCase(repository),
        getUrlUseCase = GetUrlUseCase(repository),
        setUrlUseCase = SetUrlUseCase(repository),
        getExpirationDateUseCase = GetExpirationDateUseCase(repository),
        setExpirationDateUseCase = SetExpirationDateUseCase(repository),
        createBookmarkUseCase = CreateBookmarkUseCase(repository)
    )
    override val network: BookmarkSyncNetworkComponent
            = BookmarkSyncNetworkComponentImpl(coreComponent.app.environmentDimensionNetworkComponent())
}
