package dev.bmcreations.shredder.features.list.view

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import dev.bmcreations.shredder.base.ui.BaseFragment
import dev.bmcreations.shredder.core.di.Components.BOOKMARKS_LIST
import dev.bmcreations.shredder.core.di.component
import dev.bmcreations.shredder.features.list.R
import dev.bmcreations.shredder.features.list.actions.BookmarkListActions
import dev.bmcreations.shredder.features.list.di.BookmarkListComponent
import kotlinx.android.synthetic.main.fragment_bookmark_list.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.anko.support.v4.toast


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

    private val providedArguments by lazy {
        ProvidedArguments(
            arguments?.getBoolean("from_login", false) ?: false
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        providedArguments
    }

    override fun initView() {
        toolbar.apply {
            val appBarConfiguration = AppBarConfiguration(findNavController().graph)
            setupWithNavController(findNavController(), appBarConfiguration)
            inflateMenu(R.menu.menu_app_bar)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.search -> {
                        toast("search")
                        true
                    }
                    else -> false
                }
            }
        }

        if (providedArguments.fromLogin) {
            finishOnboard()
        }

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

    private fun finishOnboard() {
        toolbar.isVisible = false
        create_fab.isExpanded = true

        lifecycleScope.launch {
            delay(400)
            create_fab.isExpanded = false
            toolbar.isVisible = true
        }
    }

    private class ProvidedArguments(val fromLogin: Boolean = false)
}
