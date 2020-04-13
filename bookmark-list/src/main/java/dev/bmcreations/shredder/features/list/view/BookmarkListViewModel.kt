package dev.bmcreations.shredder.features.list.view

import android.util.Log
import androidx.lifecycle.ViewModel
import dev.bmcreations.shredder.core.extensions.exhaustive
import dev.bmcreations.shredder.network.repository.NetworkResult
import dev.bmcreations.shredder.network.repository.WebManifestRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class IconRequest(val url: String, val result: (String?) -> Unit)

class BookmarkListViewModel private constructor(
    private val manifestFetcher: WebManifestRepository
): ViewModel(), CoroutineScope by CoroutineScope(Dispatchers.Main) {

    fun queryManifest(request: IconRequest) {
        launch(Dispatchers.IO) {
            val manifestResult = manifestFetcher.loadManifest(request.url)

            withContext(Dispatchers.Main) {
                when (manifestResult) {
                    is NetworkResult.Success -> {
                        val iconUri = manifestResult.body.highestResIcon?.src
                        request.result(request.url.plus(iconUri))
                    }
                    is NetworkResult.Failure -> {
                        Log.e("BookmarkList", "${manifestResult.errorResponse}")
                        request.result(null)
                    }
                }.exhaustive
            }
        }

    }

    companion object {
        fun create(
            manifestFetcher: WebManifestRepository
        ): BookmarkListViewModel {
            return BookmarkListViewModel(manifestFetcher)
        }
    }
}
