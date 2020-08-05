package dev.bmcreations.shredder.home.ui

import androidx.compose.Composable
import androidx.compose.collectAsState
import androidx.ui.core.ContextAmbient
import androidx.ui.core.LayoutDirection
import androidx.ui.core.Modifier
import androidx.ui.core.WithConstraints
import androidx.ui.foundation.Text
import androidx.ui.foundation.lazy.LazyColumnItems
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.layout.padding
import androidx.ui.material.Card
import androidx.ui.material.ListItem
import androidx.ui.unit.dp
import coil.request.GetRequest
import dev.bmcreations.shredder.bookmarks.Library
import dev.bmcreations.shredder.models.Bookmark
import dev.bmcreations.shredder.models.favicon
import dev.bmcreations.shredder.ui.gestures.swipeToDelete
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
internal fun BookmarkList(
    modifier: Modifier = Modifier,
    library: Library,
    onEdit: Bookmark.() -> Unit,
    onDelete: Bookmark.() -> Unit
) {
    val bookmarks = library.observeBookmarks().collectAsState(initial = emptyList())
    LazyColumnItems(modifier = modifier, items = bookmarks.value) { bookmark ->
        BookmarkCard(bookmark = bookmark, onClick = onEdit, onDelete)
    }
}

@Composable
private fun BookmarkCard(
    bookmark: Bookmark,
    onClick: (Bookmark) -> Unit,
    onDelete: Bookmark.() -> Unit
) {
    WithConstraints {
        Card(
            modifier =
            Modifier.swipeToDelete(
                constraints = constraints,
                swipeDirection = LayoutDirection.Ltr
            ) { onDelete(bookmark) } + Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
            elevation = 10.dp,
            shape = RoundedCornerShape(9.dp)
        ) {
            ListItem(
                text = { Text(text = bookmark.label) },
                secondaryText = { Text(text = bookmark.site.url) },
                icon = {
                    // CoilImage with data parameter
                    CoilImage(
                        request = GetRequest.Builder(ContextAmbient.current)
                            .data(bookmark.site.favicon)
                            .size(96)
                            .build()
                    )
                },
                onClick = { onClick(bookmark) }
            )
        }
    }
}
