package dev.bmcreations.shredder.features.create.actions

import androidx.annotation.RestrictTo
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import dev.bmcreations.shredder.features.create.R
import dev.bmcreations.shredder.models.Group

@RestrictTo(RestrictTo.Scope.LIBRARY)
object BookmarkCreateActions {
    fun createGroup(controller: NavController) = controller.navigate(R.id.create_a_group)
    fun removeGroup(controller: NavController, group: Group) =
        controller.navigate(R.id.remove_a_group, bundleOf("group" to group))

    fun selectExpiration(controller: NavController) =
        controller.navigate(R.id.select_an_expiration)
    fun removeExpiration(controller: NavController) = controller.navigate(R.id.remove_expiration)
}
