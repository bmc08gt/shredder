package dev.bmcreations.expiry.features.list

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import dev.bmcreations.expiry.base.ui.BaseFragment

class BookmarksListFragment: BaseFragment() {

    override val layoutResId: Int = R.layout.fragment_bookmark_list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun initView() {

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
