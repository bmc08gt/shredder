package dev.bmcreations.shredder.login.view

import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dev.bmcreations.shredder.base.ui.extensions.animateGone
import dev.bmcreations.shredder.base.ui.extensions.animateVisible
import dev.bmcreations.shredder.core.architecture.StateDrivenFragment
import dev.bmcreations.shredder.core.architecture.ViewStateError
import dev.bmcreations.shredder.core.architecture.ViewStateLoading
import dev.bmcreations.shredder.core.di.Components
import dev.bmcreations.shredder.core.di.component
import dev.bmcreations.shredder.login.R
import dev.bmcreations.shredder.login.actions.LoginActions
import dev.bmcreations.shredder.login.di.LoginComponent
import dev.bmcreations.shredder.login.model.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.anko.support.v4.toast


class LoginFragment :
    StateDrivenFragment<LoginViewState, LoginViewEvent, LoginViewEffect, LoginViewModel>() {

    private val component get() = Components.LOGIN.component<LoginComponent>()

    override val viewModel: LoginViewModel by lazy { component.viewModel }

    override val layoutResId: Int = R.layout.fragment_login

    override fun renderViewState(viewState: LoginViewState) {
        handleLoading(viewState.loading)
        handleError(viewState.error)

        viewState.loginInfo.apply {
            if (firstName != first_name.editText?.text?.toString()) {
                first_name.editText?.setText(firstName)
            }

            if (lastName != last_name.editText?.text?.toString()) {
                last_name.editText?.setText(lastName)
            }

            if (credentials.email != this@LoginFragment.email.editText?.text?.toString()) {
                this@LoginFragment.email.editText?.setText(credentials.email)
            }

            if (credentials.password != this@LoginFragment.password.editText?.text?.toString()) {
                this@LoginFragment.password.editText?.setText(credentials.password)
            }
        }
    }

    override fun handleLoading(loader: ViewStateLoading) {
        if (loader.loading) {
            submit.toLoading()
        }
    }

    override fun renderViewEffect(action: LoginViewEffect) {
        when (action) {
            is TriggerSwipeAlphaAnimations -> {
                submit.animateVisible(delay = 150)
                logo.animateVisible(400)
            }
            is AnimateToLogin -> {
                submit.animateColorChange(false)
                animateTextFieldsColorChange(false)
                animateNameFields(false)
            }
            is AnimateToSignUp -> {
                submit.animateColorChange(true)
                animateTextFieldsColorChange(true)
                animateNameFields(true)
            }
            DisplayErrorAuthenticating -> submit.toError()
            DisplaySuccess -> {
                submit.toSuccess()
                lifecycleScope.launch {
                    submit.animateGone(delay = 150)
                    email.animateGone(duration = 150, delay = 300)
                    password.animateGone(duration = 150, delay = 300)
                    first_name.animateGone(duration = 150, delay = 300)
                    last_name.animateGone(duration = 150, delay = 300)
                    logo.animateGone(delay = 150)
                    delay(150)
                    swipe_container.setAuthenticationSuccessful {
                        LoginActions.finishLogin(findNavController())
                    }
                }
            }
            DisableInteraction -> disableInteraction()
            EnableInteraction -> enableInteraction()
        }
    }

    override fun initView() {
        swipe_container.onInteractionListener = object : OnLoginInteractionListener {
            override fun onSwiped() = viewModel.process(Swiped)

            override fun onLoginClicked() = viewModel.process(LoginClicked)

            override fun onSignUpClicked() = viewModel.process(SignUpClicked)

            override fun onAnimationStateChanged(progress: Float) {
                email.alpha = progress
                password.alpha = progress
                title_label.alpha = 1 - progress
                tagline_label.alpha = 1f - progress
                swipe_hint_text.alpha = 1f - progress
                swipe_lottie.alpha = 1f - progress
            }
        }

        first_name.editText?.doAfterTextChanged { editable ->
            editable?.toString()?.let { viewModel.process(FirstNameUpdated(it)) }
            submit.reset()
        }

        last_name.editText?.doAfterTextChanged { editable ->
            editable?.toString()?.let { viewModel.process(LastNameUpdated(it)) }
            submit.reset()
        }

        email.editText?.doAfterTextChanged { editable ->
            editable?.toString()?.let { viewModel.process(EmailUpdated(it)) }
            submit.reset()
        }

        password.editText?.doAfterTextChanged { editable ->
            editable?.toString()?.let { viewModel.process(PasswordUpdated(it)) }
            submit.reset()
        }

        submit.setOnClickListener { viewModel.process(SubmitClicked) }
    }
}



