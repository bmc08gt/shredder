package dev.bmcreations.shredder.ui.state

import androidx.compose.*

typealias RepositoryCall <T> = ((Result<T>) -> Unit) -> Unit
typealias RepositoryCallFiltered<S, T> = (S, (Result<T>) -> Unit) -> Unit

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T?) : UiState<T>()
    data class Error(val exception: Throwable?) : UiState<Nothing>()
}

/**
 * UiState factory that updates its internal state with the [com.example.jetnews.data.Result]
 * of a repository called as a parameter.
 *
 * To load asynchronous data, effects are better pattern than using @Model classes since
 * effects are Compose lifecycle aware.
 */
@Composable
fun <T> uiStateFrom(
        repositoryCall: RepositoryCall<T>
): UiState<T> {
    var state: UiState<T> by remember { mutableStateOf(UiState.Loading) }

    // Whenever this effect is used in a composable function, it'll load data from the repository
    // when the first composition is applied
    onActive {
        repositoryCall { result ->
            state = when {
                result.isSuccess -> UiState.Success(result.getOrNull())
                result.isFailure -> UiState.Error(result.exceptionOrNull())
                else -> state
            }
        }
    }

    return state
}

@Composable
fun <S, T> uiStateFrom(
        arg: S,
        repositoryCall: RepositoryCallFiltered<S, T>
): UiState<T> {
    var state: UiState<T> by remember { mutableStateOf(UiState.Loading) }

    // Whenever this effect is used in a composable function, it'll load data from the repository
    // when the first composition is applied
    onActive {
        repositoryCall(arg) { result ->
            state = when {
                result.isSuccess -> UiState.Success(result.getOrNull())
                result.isFailure -> UiState.Error(result.exceptionOrNull())
                else -> state
            }
        }
    }

    return state
}

/**
 * Helper function that loads data from a repository call. Only use in Previews!
 */
@Composable
fun <T> previewDataFrom(
        repositoryCall: RepositoryCall<T>
): T {
    var state: T? = null
    repositoryCall { result -> state = result.getOrNull() }
    return state!!
}