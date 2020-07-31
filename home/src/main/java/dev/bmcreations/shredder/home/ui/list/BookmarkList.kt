package dev.bmcreations.shredder.home.ui.list

import androidx.compose.Composable
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Modifier
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
import dev.bmcreations.shredder.home.ui.edit.EditModel
import dev.bmcreations.shredder.home.ui.edit.edit
import dev.bmcreations.shredder.models.Bookmark
import dev.bmcreations.shredder.models.favicon
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
internal fun BookmarkList(
        padding: Modifier,
        bookmarks: List<Bookmark>,
        edit: (Bookmark) -> Unit
) {
    ScrollableColumn(modifier = padding) {
        LazyColumnItems(bookmarks) { bookmark ->
            BookmarkCard(bookmark = bookmark) { edit(it) }
        }
    }
}

@Composable
private fun BookmarkCard(bookmark: Bookmark, onClick : (Bookmark) -> Unit) {
    Card(modifier = Modifier.height(100.dp) + Modifier.padding(16.dp),
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
