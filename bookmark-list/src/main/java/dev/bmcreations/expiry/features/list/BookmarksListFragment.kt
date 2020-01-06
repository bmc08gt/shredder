package dev.bmcreations.expiry.features.list

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import dev.bmcreations.expiry.bar.BookmarksComponent
import dev.bmcreations.expiry.base.ui.BaseFragment
import dev.bmcreations.expiry.core.di.component
import dev.bmcreations.expiry.di.NetworkComponent
import dev.bmcreations.expiry.di.WebManifestNetworkComponentImpl
import dev.bmcreations.expiry.features.list.di.BookmarkListComponent
import dev.bmcreations.expiry.network.repository.WebManifestRepository
import kotlinx.android.synthetic.main.fragment_bookmark_list.*

class BookmarksListFragment : BaseFragment() {

    override val layoutResId: Int = R.layout.fragment_bookmark_list

    private val listAdapter by lazy {
        BookmarkListAdapter(
            iconRequest = { component.viewModel.queryManifest(it) }
        )
    }

    private val component
        get() = "bookmarks-list".component<BookmarkListComponent>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun initView() {
        bookmarks.adapter = listAdapter
        listAdapter.submitList(component.bookmarks.bar.bookmarks())
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
