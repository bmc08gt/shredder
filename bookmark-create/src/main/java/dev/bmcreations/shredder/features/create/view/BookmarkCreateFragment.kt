package dev.bmcreations.shredder.features.create.view

import android.content.Context
import android.os.Bundle
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import dev.bmcreations.shredder.core.architecture.StateDrivenFragment
import dev.bmcreations.shredder.core.architecture.ViewStateLoading
import dev.bmcreations.shredder.core.di.Components.BOOKMARKS_CREATE
import dev.bmcreations.shredder.core.di.component
import dev.bmcreations.shredder.features.create.OnBookmarkCreatedListener
import dev.bmcreations.shredder.features.create.R
import dev.bmcreations.shredder.features.create.actions.BookmarkCreateActions
import dev.bmcreations.shredder.features.create.di.BookmarkCreateComponent
import dev.bmcreations.shredder.features.create.view.BookmarkCreateEvent.*
import dev.bmcreations.shredder.models.Group
import kotlinx.android.synthetic.main.fragment_bookmark_create.*
import kotlinx.coroutines.launch
import java.util.*

class BookmarkCreateFragment: StateDrivenFragment<BookmarkCreateViewState, BookmarkCreateEvent, BookmarkCreateEffect, BookmarkCreateViewModel>() {

    val create get() = BOOKMARKS_CREATE.component<BookmarkCreateComponent>()

    override val viewModel: BookmarkCreateViewModel
        get() = create.viewModel

    override val layoutResId: Int = R.layout.fragment_bookmark_create

    private var onBookmarkCreated: OnBookmarkCreatedListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (parentFragment?.parentFragment is OnBookmarkCreatedListener) {
            onBookmarkCreated = parentFragment?.parentFragment as OnBookmarkCreatedListener
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        create.viewModel.process(LoadBookmark(arguments?.getString("id")))
    }

    override suspend fun whenResumed() {
        super.whenResumed()
        create.viewModel.groups().observe(viewLifecycleOwner, Observer {
            updateGroups(it ?: emptyList())
        })
        updateGroups()
    }

    override fun initView() {
        save.setOnClickListener { create.viewModel.process(Create) }
    }

    private fun updateGroups(groups: List<Group> = emptyList()) {
        chips.removeAllViews()
        chips.apply {
            groups.forEach { group ->
                addView(group.toChip(
                    context = context,
                    selected = create.viewModel.selectedGroup(),
                    onSelected = { create.viewModel.process(SelectGroup(it)) },
                    onRemove = { BookmarkCreateActions.removeGroup(findNavController(), it) })
                )
            }
            addView(context.createNewGroupChip())
        }
        chips.isSingleSelection = true
    }

    private fun loadExpiration(date: Date? = null) {
        expiration_chip_group.apply {
            removeAllViews()
            if (date == null) {
                addView(context.setExpirationChip())
            } else {
                expiration_chip_group.addView(date.toChip(context = context) {
                    BookmarkCreateActions.removeExpiration(findNavController())
                })
            }
        }
    }

    override fun renderViewState(viewState: BookmarkCreateViewState) {
        when {
            viewState.loading.isLoading() -> {}
            viewState.error.hasErrors() -> handleError(viewState.error)
            else -> {
                val data = viewState.data
                label.editText?.setText(data.title)
                url.editText?.setText(data.url)
                lifecycleScope.launch {
                    create.viewModel.groups().value.let {
                        updateGroups(it ?: emptyList())
                    }
                }
                loadExpiration(data.expiration)
                label.editText?.doAfterTextChanged { text ->
                    create.viewModel.process(LabelUpdated(text?.toString()))
                }
                url.editText?.doAfterTextChanged { text ->
                    create.viewModel.process(UrlUpdated(text?.toString()))
                }
            }
        }
    }

    override fun renderViewEffect(action: BookmarkCreateEffect) = Unit
    override fun handleLoading(loader: ViewStateLoading) = Unit
}
