package dev.bmcreations.shredder.core.architecture

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast

abstract class StateDrivenActivity<T: ViewState, E: ViewStateEvent, X: ViewStateEffect, V : BaseViewModel<T, E, X>> : AppCompatActivity() {

    abstract val viewModel: V

    abstract val layoutResId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)

        lifecycleScope.launchWhenStarted { whenStarted() }
        lifecycleScope.launchWhenResumed { whenResumed() }
        viewModel.state.observe(this, viewStateObserver)
        viewModel.effects.observe(this, viewEffectsObserver)
    }

    protected open suspend fun whenStarted() = Unit
    protected open suspend fun whenResumed() = Unit

    abstract fun renderViewState(viewState: T)
    abstract fun renderViewEffect(action: X)
    abstract fun handleLoading(loader: ViewStateLoading)

    protected open fun handleError(error: ViewStateError) {
        if (error.hasErrors()) {
            toast(error.message(this))
        }
    }


    private val viewStateObserver = Observer<T> {
        Log.d(javaClass.simpleName, "observed viewState : $it")
        renderViewState(it)
    }

    private val viewEffectsObserver = Observer<X> {
        Log.d(javaClass.simpleName,"observed view action : $it")
        renderViewEffect(it)
    }
}
