package dev.bmcreations.shredder.core.architecture

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar

abstract class StateDrivenActivity<T: ViewState, E: ViewStateEvent, X: ViewStateEffect, V : BaseViewModel<T, E, X>> : AppCompatActivity() {

    abstract val viewModel: V

    abstract val layoutResId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)

        lifecycleScope.launchWhenStarted { whenStarted() }
        lifecycleScope.launchWhenResumed { whenResumed() }
        viewModel.state.observe(this, viewStateObserver)
        viewModel.events.observe(this, viewEventObserver)
        viewModel.effects.observe(this, viewEffectsObserver)
    }

    protected open suspend fun whenStarted() = Unit
    protected open suspend fun whenResumed() = Unit

    abstract fun renderViewState(viewState: T)
    abstract fun renderViewEffect(action: X)
    abstract fun handleEvent(event: E)

    protected fun handleError(error: Error) {
        window.decorView.rootView.doOnLayout {
            Snackbar.make(it, error.message(this), Snackbar.LENGTH_SHORT).show()
        }
    }

    private val viewStateObserver = Observer<T> {
        Log.d(javaClass.simpleName, "observed viewState : $it")
        renderViewState(it)
    }

    private val viewEventObserver = Observer<E> {
        Log.d(javaClass.simpleName,"observed view action : $it")
        handleEvent(it)
    }

    private val viewEffectsObserver = Observer<X> {
        Log.d(javaClass.simpleName,"observed view action : $it")
        renderViewEffect(it)
    }
}
