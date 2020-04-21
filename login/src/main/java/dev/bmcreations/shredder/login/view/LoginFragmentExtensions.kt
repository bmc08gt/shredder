package dev.bmcreations.shredder.login.view

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.annotation.ColorInt
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import dev.bmcreations.shredder.base.ui.extensions.animateGone
import dev.bmcreations.shredder.base.ui.extensions.animateVisible
import dev.bmcreations.shredder.base.ui.extensions.shake
import dev.bmcreations.shredder.core.extensions.colors
import dev.bmcreations.shredder.login.R
import kotlinx.android.synthetic.main.fragment_login.*


@SuppressLint("ObjectAnimatorBinding")
internal fun FloatingActionButton.animateColorChange(toSignUp: Boolean) {
    animateColorChange(
        context.colors[if (toSignUp) R.color.color_secondary else R.color.color_login_sign_in_background],
        context.colors[if (toSignUp) R.color.color_login_sign_in_background else R.color.color_secondary]
    )
}

@SuppressLint("ObjectAnimatorBinding")
internal fun FloatingActionButton.animateColorChange(@ColorInt fromColor: Int, @ColorInt toColor: Int, startDelay: Long = 0) {
    val colorAnimator = ObjectAnimator.ofArgb(
        this,
        "backgroundTintColor",
        fromColor, toColor
    ).apply {
        setStartDelay(startDelay)
        interpolator = AccelerateDecelerateInterpolator()
        addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Int
            backgroundTintList = ColorStateList.valueOf(animatedValue)
        }
    }

    colorAnimator.start()
}

internal fun FloatingActionButton.reset() {
    val progressDrawable = drawable as? CircularProgressDrawable
    progressDrawable?.stop()
    setImageResource(R.drawable.ic_arrow_right_color_control_normal_24dp)
}

internal fun FloatingActionButton.toLoading() {
    val drawable = CircularProgressDrawable(context).apply {
        strokeWidth = 8f
    }
    setImageDrawable(drawable).also { drawable.start() }
}

internal fun FloatingActionButton.toSuccess() {
    val progressDrawable = drawable as? CircularProgressDrawable
    progressDrawable?.stop()
    setImageResource(R.drawable.ic_check_color_control_normal_24dp)
}

internal fun FloatingActionButton.toError() {
    val progressDrawable = drawable as? CircularProgressDrawable
    progressDrawable?.stop()

    setImageResource(R.drawable.ic_alert_circle_outline_color_control_normal_24dp)

    // animate color from normal to error red and back
    animateColorChange(context.colors[R.color.color_secondary], context.colors[R.color.color_error])
    animateColorChange(context.colors[R.color.color_error], context.colors[R.color.color_secondary], 300)

    // shake and vibe
    shake(vibrate = true)
}

internal fun LoginFragment.animateTextFieldsColorChange(toSignUp: Boolean) {
    val animations = AnimatorSet()

    animations.playTogether(
        *animateTextFieldColorChange(email, toSignUp).toTypedArray(),
        *animateTextFieldColorChange(password, toSignUp).toTypedArray(),
        *animateTextFieldColorChange(first_name, toSignUp).toTypedArray(),
        *animateTextFieldColorChange(last_name, toSignUp).toTypedArray()
    )
    animations.start()
}

@SuppressLint("ObjectAnimatorBinding")
private fun animateTextFieldColorChange(
    layout: TextInputLayout,
    toSignUp: Boolean
): List<ObjectAnimator> {
    return listOf(
        ObjectAnimator.ofArgb(
            layout,
            "boxStrokeColor",
            layout.context.colors[if (toSignUp) R.color.color_secondary else R.color.color_login_sign_in_background],
            layout.context.colors[if (toSignUp) R.color.color_login_sign_in_background else R.color.color_secondary]
        ).apply { interpolator = AccelerateDecelerateInterpolator() },

        ObjectAnimator.ofArgb(
            layout,
            "hintTextColor",
            layout.context.colors[if (toSignUp) R.color.color_secondary else R.color.color_login_sign_in_background],
            layout.context.colors[if (toSignUp) R.color.color_login_sign_in_background else R.color.color_secondary]
        ).apply {
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener { animation ->
                val animatedValue = animation.animatedValue as Int
                layout.hintTextColor = ColorStateList.valueOf(animatedValue)
            }
        })
}

internal fun LoginFragment.disableInteraction() {
    first_name.isEnabled = false
    last_name.isEnabled = false
    email.isEnabled = false
    password.isEnabled = false
    swipe_container.isEnabled = false
    submit.isEnabled = false
}

internal fun LoginFragment.enableInteraction() {
    first_name.isEnabled = true
    last_name.isEnabled = true
    email.isEnabled = true
    password.isEnabled = true
    swipe_container.isEnabled = true
    submit.isEnabled = true
}

internal fun LoginFragment.animateNameFields(toSignUp: Boolean) {
    if (toSignUp) {
        first_name.animateVisible(300)
        last_name.animateVisible(300)
    } else {
        first_name.animateGone(300)
        last_name.animateGone(300)
    }
}
