package dev.bmcreations.shredder.features.create

import android.app.Dialog
import android.graphics.Rect
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.bmcreations.shredder.core.architecture.observe
import dev.bmcreations.shredder.core.di.Components
import dev.bmcreations.shredder.core.di.component
import dev.bmcreations.shredder.core.extensions.dp
import dev.bmcreations.shredder.core.lifecycle.ProvidedArguments
import dev.bmcreations.shredder.features.create.di.BookmarkCreateComponent
import dev.bmcreations.shredder.features.create.view.BookmarkCreateEvent
import kotlinx.android.synthetic.main.dialog_bookmark_creator.*
import kotlinx.coroutines.launch

interface OnBookmarkCreatedListener {
    fun onBookmarkCreated()
}

class BookmarkCreator : BottomSheetDialogFragment(), OnBookmarkCreatedListener {

    private val createComponent
        get() = Components.BOOKMARKS_CREATE.component<BookmarkCreateComponent>()

    private val providedArguments by lazy {
        val url = arguments?.getString(URL)
        val label = arguments?.getString(LABEL)
        Args(url, label)
    }

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        providedArguments // touch to read arguments
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(
            R.layout.dialog_bookmark_creator,
            container,
            false
        )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // We can't inflate the NavHostFragment from XML because it will crash the 2nd time the dialog is opened
        val navHost = NavHostFragment()
        childFragmentManager.beginTransaction().replace(R.id.navHost, navHost)
            .commitNow()

        navController = navHost.navController

        navHost.navController.setGraph(R.navigation.navigation_create)
        toolbar.setupWithNavController(navHost.navController)

        lifecycleScope.launch {
            val bookmarks = createComponent.bookmarks.bar.bookmarks()
            createComponent.bookmarks.bar.observeBookmarks().observe(viewLifecycleOwner, Observer {
                if (it != bookmarks) {
                    dismiss()
                }
            })
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            theme
            setOnShowListener { dialog ->
                val d = dialog as BottomSheetDialog
                val bottomSheet =
                    d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
                // set bottom sheet height and peekHeight to equal values
                // this, in combination with isHideable = false, creates a static modal locked sheet
                val maxHeight = maximumHeight(activity?.findViewById(R.id.toolbar), 56.dp)
                BottomSheetBehavior.from(bottomSheet).apply {
                    isHideable = false
                    peekHeight = maxHeight
                }
                bottomSheet.layoutParams?.height = maxHeight
            }
            // Normally the dialog would close on back press. We override this behaviour and check if we can navigate back
            // If we can't navigate back we return false triggering the default implementation closing the dialog
            setOnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                    val popped = navController.popBackStack()
                    if (popped) {
                        true
                    } else {
                        findNavController().popBackStack()
                    }
                } else {
                    false
                }
            }
        }
    }

    private data class Args(
        val url: String?,
        val label: String?
    ) : ProvidedArguments<BookmarkCreator>()

    companion object {
        const val TAG = "Bookmark.Create"

        const val URL = "url"
        const val LABEL = "label"
    }

    override fun onBookmarkCreated() {
        val popped = navController.popBackStack()
        if (!popped) {
            findNavController().popBackStack()
        }
    }
}


fun BottomSheetDialogFragment.maximumHeight(
    toolbar: Toolbar? = activity?.findViewById(R.id.toolbar),
    padding: Int
): Int {
    val toolbarHeight = toolbar?.height ?: 0
    val statusbar = Rect().apply { activity?.window?.decorView?.getWindowVisibleDisplayFrame(this) }
    val windowHeight = statusbar.bottom
    return windowHeight - toolbarHeight - statusbar.top - padding
}
