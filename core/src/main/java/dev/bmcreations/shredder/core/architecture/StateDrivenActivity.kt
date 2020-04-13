package dev.bmcreations.shredder.core.architecture

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar

abstract class StateDrivenActivity<T: ViewState, E: ViewStateEvent, A: ViewStateEffect, V : BaseViewModel<T, E, A>> : AppCompatActivity() {

    abstract val viewModel: V

    abstract val layoutResId: Int

    abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)
        initView()

        lifecycleScope.launchWhenStarted { whenStarted() }
        lifecycleScope.launchWhenResumed { whenResumed() }
        viewModel.state.observe(this, viewStateObserver)
        viewModel.events.observe(this, viewEventObserver)
        viewModel.effects.observe(this, viewEffectsObserver)
    }

    protected fun whenStarted() = Unit
    protected  fun whenResumed() = Unit

    abstract fun renderViewState(viewState: T)
    abstract fun handleEvent(event: E)
    abstract fun handleAction(action: A)

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

    private val viewEffectsObserver = Observer<A> {
        Log.d(javaClass.simpleName,"observed view action : $it")
        handleAction(it)
    }
}
