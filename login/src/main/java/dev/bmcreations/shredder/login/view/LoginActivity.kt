package dev.bmcreations.shredder.login.view

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.content.res.ColorStateList
import android.util.Log
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.view.ViewCompat
import dev.bmcreations.shredder.core.architecture.StateDrivenActivity
import dev.bmcreations.shredder.core.di.Components
import dev.bmcreations.shredder.core.di.component
import dev.bmcreations.shredder.core.extensions.animateGone
import dev.bmcreations.shredder.core.extensions.animateVisible
import dev.bmcreations.shredder.core.extensions.colors
import dev.bmcreations.shredder.login.R
import dev.bmcreations.shredder.login.di.LoginComponent
import dev.bmcreations.shredder.login.model.LoginViewEffect
import dev.bmcreations.shredder.login.model.LoginViewEvent
import dev.bmcreations.shredder.login.model.LoginViewState
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : StateDrivenActivity<LoginViewState, LoginViewEvent, LoginViewEffect, LoginViewModel>() {

    private val component  get() = Components.LOGIN.component<LoginComponent>()

    override val viewModel: LoginViewModel by lazy { component.viewModel }

    override val layoutResId: Int = R.layout.activity_login

    override suspend fun whenStarted() {
        swipe_container.onInteractionListener = object : OnLoginInteractionListener {
            override fun onSwiped() {
                submit.animateVisible(delay = 150)
                logo.animateVisible(400)
            }

            override fun onLoginClicked() {
                animateFabColorChange(false)
                animateNameFields(false)
            }

            override fun onSignUpClicked() {
                animateFabColorChange(true)
                animateNameFields(true)
            }

            override fun onAnimationStateChanged(progress: Float) {
                email.alpha = progress
                password.alpha = progress
                title_label.alpha = 1 - progress
                tagline_label.alpha = 1f - progress
                swipe_hint_text.alpha = 1f - progress
                swipe_lottie.alpha = 1f - progress
            }
        }
    }

    override fun renderViewState(viewState: LoginViewState) {
    }

    override fun renderViewEffect(action: LoginViewEffect) {
    }

    override fun handleEvent(event: LoginViewEvent) {
    }

    fun animateFabColorChange(toSignUp: Boolean) {
        val colorAnimator = ObjectAnimator.ofArgb(
            submit.background.mutate(),
            "tint",
            colors[if (toSignUp) R.color.color_secondary else R.color.color_login_sign_in_background],
            colors[if (toSignUp) R.color.color_login_sign_in_background else R.color.color_secondary]
        )
        colorAnimator.interpolator = AccelerateDecelerateInterpolator()
        colorAnimator.start()
    }

    fun animateNameFields(toSignUp: Boolean) {
        if (toSignUp) {
            first_name.animateVisible(300)
            last_name.animateVisible(300)
        } else {
            first_name.animateGone(300)
            last_name.animateGone(300)
        }
    }
}




