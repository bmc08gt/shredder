package dev.bmcreations.shredder.ui.state

import androidx.compose.*

/**
 * Model for UiStates that can refresh. The Success state contains whether there's data loading
 * apart from the current data to display on the screen. The error state also returns previously
 * loaded data apart from the error.
 */
sealed class RefreshableUiState<out T> {
    data class Success<out T>(val data: T?, val loading: Boolean) : RefreshableUiState<T>()
    data class Error<out T>(val exception: Throwable?, val previousData: T?) :
            RefreshableUiState<T>()
}

/**
 * Handler that allows getting the current RefreshableUiState and refresh its content.
 */
data class RefreshableUiStateHandler<T>(
        val state: RefreshableUiState<T>,
        val refreshAction: () -> Unit
)

/**
 * Refreshable UiState factory that updates its internal state with the
 * [com.example.jetnews.data.Result] of a callback passed as a parameter.
 *
 * To load asynchronous data, effects are better pattern than using @Model classes since
 * effects are Compose lifecycle aware.
 */
@Composable
fun <T> refreshableUiStateFrom(
        repositoryCall: RepositoryCall<T>
): RefreshableUiStateHandler<T> {

    var state: RefreshableUiState<T> by remember {
        mutableStateOf(RefreshableUiState.Success(data = null, loading = true))
    }

    val refresh = {
        state = RefreshableUiState.Success(data = state.currentData, loading = true)
        repositoryCall { result ->
            state = when {
                result.isSuccess -> RefreshableUiState.Success(data = result.getOrNull(), loading = false)
                result.isFailure -> RefreshableUiState.Error(exception = result.exceptionOrNull(), previousData = state.currentData)
                else -> state
            }
        }
    }

    onActive {
        refresh()
    }

    return RefreshableUiStateHandler(state, refresh)
}

@Composable
fun <S, T> refreshableUiStateFrom(
        arg: S,
        repositoryCall: RepositoryCallFiltered<S, T>
): RefreshableUiStateHandler<T> {

    var state: RefreshableUiState<T> by remember {
        mutableStateOf(RefreshableUiState.Success(data = null, loading = true))
    }

    val refresh = {
        state = RefreshableUiState.Success(data = state.currentData, loading = true)
        repositoryCall(arg) { result ->
            state = when {
                result.isSuccess -> RefreshableUiState.Success(data = result.getOrNull(), loading = false)
                result.isFailure -> RefreshableUiState.Error(exception = result.exceptionOrNull(), previousData = state.currentData)
                else -> state
            }
        }
    }

    onActive {
        refresh()
    }

    return RefreshableUiStateHandler(state, refresh)
}

val <T> RefreshableUiState<T>.loading: Boolean
    get() = this is RefreshableUiState.Success && this.loading && this.data == null

val <T> RefreshableUiState<T>.refreshing: Boolean
    get() = this is RefreshableUiState.Success && this.loading && this.data != null

val <T> RefreshableUiState<T>.currentData: T?
    get() = when (this) {
        is RefreshableUiState.Success -> this.data
        is RefreshableUiState.Error -> this.previousData
    }