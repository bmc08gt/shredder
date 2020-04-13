package dev.bmcreations.shredder.features.create.view

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import dev.bmcreations.shredder.base.ui.BaseFragment
import dev.bmcreations.shredder.core.architecture.Error
import dev.bmcreations.shredder.core.di.Components.BOOKMARKS_CREATE
import dev.bmcreations.shredder.core.di.component
import dev.bmcreations.shredder.features.create.OnBookmarkCreatedListener
import dev.bmcreations.shredder.features.create.R
import dev.bmcreations.shredder.features.create.actions.BookmarkCreateActions
import dev.bmcreations.shredder.features.create.di.BookmarkCreateComponent
import dev.bmcreations.shredder.models.Group
import kotlinx.android.synthetic.main.fragment_bookmark_create.*
import kotlinx.coroutines.launch
import java.util.*

class BookmarkCreateFragment: BaseFragment() {

    val create get() = BOOKMARKS_CREATE.component<BookmarkCreateComponent>()

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
        create.viewModel.loadBookmark(arguments?.getString("id"))
    }

    override fun initView() {
        lifecycleScope.launchWhenResumed {
            create.viewModel.groups().observe(viewLifecycleOwner, Observer {
                updateGroups(it ?: emptyList())
            })
            updateGroups()
        }

        save.setOnClickListener { create.viewModel.createBookmark() }

        observe()
    }

    private fun observe() {
        create.viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            when {
                state.loading.isLoading() -> {}
                state.error.hasErrors() -> handleError(state.error)
                else -> {
                    val data = state.data
                    label.editText?.setText(data.title)
                    url.editText?.setText(data.url)
                    lifecycleScope.launch {
                        create.viewModel.groups().value.let {
                            updateGroups(it ?: emptyList())
                        }
                    }
                    loadExpiration(data.expiration)
                    label.editText?.doAfterTextChanged { text ->
                        create.viewModel.updateLabel(text?.toString())
                    }
                    url.editText?.doAfterTextChanged { text ->
                        create.viewModel.updateUrl(text?.toString())
                    }
                }
            }
        })
    }

    private fun handleError(error: Error) {
        context?.let { Toast.makeText(it, error.message(it), Toast.LENGTH_SHORT).show() }
    }

    private fun updateGroups(groups: List<Group> = emptyList()) {
        chips.removeAllViews()
        chips.apply {
            groups.forEach { group ->
                addView(group.toChip(
                    context = context,
                    selected = create.viewModel.selectedGroup(),
                    onSelected = { create.viewModel.selectGroup(it) },
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

}
