package dev.bmcreations.shredder.features.list.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dev.bmcreations.shredder.base.ui.BaseFragment
import dev.bmcreations.shredder.core.di.Components.BOOKMARKS_LIST
import dev.bmcreations.shredder.core.di.component
import dev.bmcreations.shredder.features.list.R
import dev.bmcreations.shredder.features.list.actions.BookmarkListActions
import dev.bmcreations.shredder.features.list.di.BookmarkListComponent
import kotlinx.android.synthetic.main.fragment_bookmark_list.*
import kotlinx.coroutines.launch

class BookmarksListFragment : BaseFragment() {

    override val layoutResId: Int =
        R.layout.fragment_bookmark_list

    private val listAdapter by lazy {
        BookmarkListAdapter(
            iconRequest = { component.viewModel.queryManifest(it) }
        )
    }

    private val component
        get() = BOOKMARKS_LIST.component<BookmarkListComponent>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun initView() {
        bookmarks.adapter = listAdapter
        lifecycleScope.launch {
            component.bookmarks.bar.observeBookmarks().observe(viewLifecycleOwner, Observer {
                listAdapter.submitList(it)
            })
        }

        create_fab.setOnClickListener {
            BookmarkListActions.openSheet(findNavController())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_app_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search -> {
                // TODO: search
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
