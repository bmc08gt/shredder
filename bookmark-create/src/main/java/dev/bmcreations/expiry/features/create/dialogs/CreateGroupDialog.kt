package dev.bmcreations.expiry.features.create.dialogs

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dev.bmcreations.expiry.bar.BookmarksComponent
import dev.bmcreations.expiry.core.di.Components
import dev.bmcreations.expiry.core.di.Components.BOOKMARKS
import dev.bmcreations.expiry.core.di.component
import dev.bmcreations.expiry.features.create.R
import dev.bmcreations.expiry.models.Group
import kotlinx.android.synthetic.main.dialog_group_create.view.*

class CreateGroupDialog : DialogFragment() {

    private val bookmarks = BOOKMARKS.component<BookmarksComponent>()

    private lateinit var root: View

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(context, theme).apply {
            setTitle("Create A New Bookmark Group")
           root =
                LayoutInflater.from(context).inflate(R.layout.dialog_group_create, null, false)
            setView(root)
            setPositiveButton("OK") { dialog, _ ->
                // on success
                val label = root.name.editText?.text.toString()
                if (label.isNotEmpty()) {
                    dialog.dismiss()
                    bookmarks.bar.upsert(Group(name = label))
                } else {
                    Toast.makeText(
                        context,
                        "Please enter a name for your group",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        }.create()
    }

    override fun onResume() {
        super.onResume()
        root.name.requestFocus()
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }
}
