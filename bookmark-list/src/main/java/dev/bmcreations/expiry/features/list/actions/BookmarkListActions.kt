package dev.bmcreations.expiry.features.list.actions

import android.net.Uri
import androidx.navigation.NavController


object BookmarkListActions {
    fun openSheet(controller: NavController) = controller.navigate(Uri.parse("expiry://create"))
}
