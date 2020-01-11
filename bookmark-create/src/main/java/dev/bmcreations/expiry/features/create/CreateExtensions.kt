package dev.bmcreations.expiry.features.create

import android.content.Context
import android.widget.CompoundButton
import com.google.android.material.chip.Chip
import dev.bmcreations.expiry.core.extensions.drawables
import dev.bmcreations.expiry.models.Group

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
