package dev.bmcreations.expiry.features.create.di

import dev.bmcreations.expiry.bar.BookmarksComponent
import dev.bmcreations.expiry.core.di.Component
import dev.bmcreations.expiry.features.create.BookmarkCreateViewModel
import dev.bmcreations.expiry.features.create.repository.BookmarkCreateRepository
import dev.bmcreations.expiry.features.create.repository.BookmarkCreateRepositoryImpl
import dev.bmcreations.expiry.features.create.usecases.*

interface BookmarkCreateComponent : Component {
    val bookmarks: BookmarksComponent
    val viewModel: BookmarkCreateViewModel
}

class BookmarkCreateComponentImpl(
    override val bookmarks: BookmarksComponent
) : BookmarkCreateComponent {
    private val repository get() = BookmarkCreateRepositoryImpl(bookmarks)

    override val viewModel
        get() = BookmarkCreateViewModel.create(
            loadBookmarkUseCase = LoadBookmarkUsecase(repository),
            getGroupsUseCase = GetGroupsLiveDataUsecase(repository),
            getSelectedGroupUseCase = GetSelectedGroupUsecase(repository),
            setSelectedGroupUseCase = SelectGroupUsecase(repository),
            createBookmarkUseCase = CreateBookmarkUsecase(repository)
        )
}
