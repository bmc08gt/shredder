package dev.bmcreations.expiry.features.create.view

import android.content.Context
import android.widget.Toast
import androidx.core.view.get
import androidx.core.view.size
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.google.android.material.chip.Chip
import dev.bmcreations.expiry.base.ui.BaseFragment
import dev.bmcreations.expiry.core.architecture.Error
import dev.bmcreations.expiry.core.di.Components.BOOKMARKS_CREATE
import dev.bmcreations.expiry.core.di.component
import dev.bmcreations.expiry.features.create.OnBookmarkCreatedListener
import dev.bmcreations.expiry.features.create.R
import dev.bmcreations.expiry.features.create.actions.BookmarkCreateActions
import dev.bmcreations.expiry.features.create.di.BookmarkCreateComponent
import dev.bmcreations.expiry.features.create.view.BookmarkCreateViewState.StateRequest
import dev.bmcreations.expiry.features.create.view.BookmarkCreateViewState.StateResult
import dev.bmcreations.expiry.models.Group
import kotlinx.android.synthetic.main.fragment_bookmark_create.*

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

    override fun initView() {
        updateGroups()
        lifecycleScope.launchWhenCreated {
            create.viewModel.groups().observe(viewLifecycleOwner, Observer {
                updateGroups(it ?: emptyList())
            })
        }

        save.setOnClickListener {
            create.viewModel.createBookmark(
                title = label.editText?.text.toString(),
                url = url.editText?.text.toString(),
                group = findGroup()
            )
        }

        observe()
    }

    private fun observe() {
        create.viewModel.state.observe(this, Observer {
            it.content()?.let { state ->
                when {
                    state.loading -> {}
                    state.request != StateRequest.None-> handleRequest(state.request)
                    state.result != StateResult.None -> handleResult(state.result)
                    state.error.hasErrors() -> handleError(state.error)
                }
            }
        })
    }

    private fun handleRequest(request: StateRequest) {
        when (request) {
            is StateRequest.InitialLoad -> create.viewModel.loadBookmark(request.bookmarkId)
        }
    }

    private fun handleResult(result: StateResult) {

    }

    private fun handleError(error: Error) {
        context?.let { Toast.makeText(it, error.message(it), Toast.LENGTH_SHORT).show() }
    }

    private fun findGroup(): Group? {
        for (i in 0 until chips.size) {
            val chip = chips[i] as Chip
            if (chip.isChecked) {
                return chip.tag as Group
            }
        }
        return null
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

            val createGroup = Chip(context).apply {
                text = "Create new"
                isClickable = true
                setOnClickListener { BookmarkCreateActions.createGroup(findNavController()) }
            }
            addView(createGroup)
        }
        chips.isSingleSelection = true
    }

}
