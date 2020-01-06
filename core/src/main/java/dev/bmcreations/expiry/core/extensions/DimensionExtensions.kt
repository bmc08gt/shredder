package dev.bmcreations.expiry.core.extensions

import android.content.res.Resources
import android.util.TypedValue
import kotlin.math.roundToInt

val Number.dp: Int
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).roundToInt()

val Number.sp: Int
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).roundToInt()
