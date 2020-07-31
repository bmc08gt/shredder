package dev.bmcreations.shredder.home.ui.list

import android.os.Vibrator
import androidx.compose.Composable
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Modifier
import androidx.ui.core.WithConstraints
import androidx.ui.foundation.ScrollableColumn
import androidx.ui.foundation.Text
import androidx.ui.foundation.lazy.LazyColumnItems
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.layout.height
import androidx.ui.layout.padding
import androidx.ui.material.Card
import androidx.ui.material.ListItem
import androidx.ui.unit.dp
import coil.request.GetRequest
import dev.bmcreations.shredder.models.Bookmark
import dev.bmcreations.shredder.models.favicon
import dev.bmcreations.shredder.ui.gestures.swipeToDelete
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
internal fun BookmarkList(
    padding: Modifier,
    bookmarks: List<Bookmark>,
    edit: (Bookmark) -> Unit,
    delete: (Bookmark) -> Unit
) {
    ScrollableColumn(modifier = padding) {
        LazyColumnItems(bookmarks) { bookmark ->
            BookmarkCard(bookmark = bookmark, onClick = edit, onSwipe = delete)
        }
    }
}

@Composable
private fun BookmarkCard(
    bookmark: Bookmark,
    onClick: (Bookmark) -> Unit,
    onSwipe: (Bookmark) -> Unit
) {
    WithConstraints {
        Card(
            modifier = Modifier.height(100.dp) +
                Modifier.padding(16.dp) +
                Modifier.swipeToDelete(constraints) { onSwipe(bookmark) },
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
