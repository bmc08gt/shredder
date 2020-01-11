package dev.bmcreations.expiry.features.create.dialogs

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dev.bmcreations.expiry.bar.BookmarksComponent
import dev.bmcreations.expiry.core.di.Components
import dev.bmcreations.expiry.core.di.Components.BOOKMARKS
import dev.bmcreations.expiry.core.di.component
import dev.bmcreations.expiry.models.Group

class RemoveGroupDialog : DialogFragment() {

    private val bookmarks = BOOKMARKS.component<BookmarksComponent>()

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val group = arguments?.getParcelable<Group>("group")

        return MaterialAlertDialogBuilder(context, theme).apply {
            setTitle("Are you sure?")
            setMessage("Removing a group will unassign all boomarks from this group.")
            setPositiveButton("Yes") { dialog, _ ->
                group?.let { bookmarks.bar.remove(it) }
                dialog.dismiss()
            }
            setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
        }.create()
    }
}
