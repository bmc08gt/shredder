package dev.bmcreations.shredder.features.create.di

import com.jakewharton.threetenabp.AndroidThreeTen
import dev.bmcreations.shredder.bar.BookmarksComponent
import dev.bmcreations.shredder.core.di.Component
import dev.bmcreations.shredder.core.di.CoreComponent
import dev.bmcreations.shredder.features.create.repository.BookmarkCreateRepositoryImpl
import dev.bmcreations.shredder.features.create.usecases.*
import dev.bmcreations.shredder.features.create.view.BookmarkCreateViewModel

interface BookmarkCreateComponent : Component {
    val bookmarks: BookmarksComponent
    val viewModel: BookmarkCreateViewModel
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
        loadBookmarkUseCase = LoadBookmarkUsecase(repository),
        getGroupsUseCase = GetGroupsLiveDataUsecase(repository),
        getSelectedGroupUseCase = GetSelectedGroupUsecase(repository),
        setSelectedGroupUseCase = SelectGroupUsecase(repository),
        getTitleUseCase = GetTitleUsecase(repository),
        setTitleUseCase = SetTitleUsecase(repository),
        getUrlUseCase = GetUrlUsecase(repository),
        setUrlUseCase = SetUrlUsecase(repository),
        getExpirationDateUseCase = GetExpirationDateUsecase(repository),
        setExpirationDateUseCase = SetExpirationDateUsecase(repository),
        createBookmarkUseCase = CreateBookmarkUsecase(repository)
    )
}
