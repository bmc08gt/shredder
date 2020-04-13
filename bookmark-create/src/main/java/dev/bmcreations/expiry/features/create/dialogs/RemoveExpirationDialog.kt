package dev.bmcreations.expiry.features.create.dialogs

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dev.bmcreations.expiry.core.di.Components
import dev.bmcreations.expiry.core.di.component
import dev.bmcreations.expiry.features.create.di.BookmarkCreateComponent
import dev.bmcreations.expiry.models.Group
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class RemoveExpirationDialog : DialogFragment() {

    private val create = Components.BOOKMARKS_CREATE.component<BookmarkCreateComponent>()

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(context, theme).apply {
            setTitle("Are you sure?")
            setPositiveButton("Yes") { dialog, _ ->
                create.viewModel.onExpirationSet(null)
                dialog.dismiss()
            }
            setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
        }.create()
    }
}

