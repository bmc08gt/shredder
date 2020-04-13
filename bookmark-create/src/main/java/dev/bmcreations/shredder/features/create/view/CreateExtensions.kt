package dev.bmcreations.shredder.features.create.view

import android.content.Context
import android.widget.CompoundButton
import androidx.navigation.findNavController
import com.google.android.material.chip.Chip
import dev.bmcreations.shredder.core.extensions.drawables
import dev.bmcreations.shredder.core.extensions.format
import dev.bmcreations.shredder.features.create.R
import dev.bmcreations.shredder.features.create.actions.BookmarkCreateActions
import dev.bmcreations.shredder.models.Group
import java.util.*

fun Group.toChip(
    context: Context,
    selected: Group?,
    onSelected: ((group: Group) -> Unit),
    onRemove: ((group: Group) -> Unit)
): Chip = Chip(
    context,
    null).apply {
    text = name
    tag = this@toChip
    isClickable = true
    isCheckable = true
    isChecked = selected == this@toChip
    closeIcon = context.drawables[R.drawable.ic_close_circle_color_control_normal_24dp]
    isCloseIconVisible = true
    setOnCloseIconClickListener { onRemove.invoke(this@toChip) }
    setOnCheckedChangeListener { _: CompoundButton?, b: Boolean ->
        if (b) {
            onSelected.invoke(this@toChip)
        }
    }
}

fun Date.toChip(
    context: Context,
    onRemove: (() -> Unit)
) : Chip = Chip(
    context,
    null).apply {
    text = this@toChip.format("MM-dd-yyyy")
    isClickable = true
    closeIcon = context.drawables[R.drawable.ic_close_circle_color_control_normal_24dp]
    isCloseIconVisible = true
    setOnCloseIconClickListener {
        onRemove.invoke()
    }
}

fun Context.createNewGroupChip(): Chip = Chip(this).apply {
    text = "Create new"
    isClickable = true
    setOnClickListener { BookmarkCreateActions.createGroup(findNavController()) }
}

fun Context.setExpirationChip(): Chip =  Chip(this).apply {
    text = "Set"
    isClickable = true
    setOnClickListener { BookmarkCreateActions.selectExpiration(findNavController()) }
}
