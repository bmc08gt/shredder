package dev.bmcreations.shredder.login.actions

import android.net.Uri
import androidx.navigation.NavController

object LoginActions {
    fun finishLogin(controller: NavController) {
        controller.navigate(Uri.parse("shredder.app://list?from_login=true"))
    }
}

