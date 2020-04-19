package dev.bmcreations.shredder.core.architecture

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar

abstract class StateDrivenFragment<T: ViewState, E: ViewStateEvent, X: ViewStateEffect, V : BaseViewModel<T, E, X>> : Fragment() {

    abstract val viewModel: V

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

    abstract val layoutResId: Int

    abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenStarted { whenStarted() }
        lifecycleScope.launchWhenResumed { whenResumed() }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutResId, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        viewModel.state.observe(viewLifecycleOwner, viewStateObserver)
        viewModel.events.observe(viewLifecycleOwner, viewEventObserver)
        viewModel.effects.observe(viewLifecycleOwner, viewEffectsObserver)
    }

    open suspend fun whenStarted() = Unit
    open suspend fun whenResumed() = Unit

    abstract fun renderViewState(viewState: T)
    abstract fun handleEvent(event: E)
    abstract fun renderViewEffect(action: X)

    protected fun handleError(error: ViewStateError) {
        activity?.window?.decorView?.rootView?.doOnLayout { decorView ->
            context?.let {
                Snackbar.make(decorView, error.message(it), Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}
