package dev.bmcreations.expiry.features.list.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import dev.bmcreations.expiry.features.list.R
import dev.bmcreations.expiry.models.Bookmark
import kotlinx.android.synthetic.main.bookmark_list_item.view.*

class BookmarkListAdapter(
    val iconRequest: ((IconRequest) -> Unit)
) : ListAdapter<Bookmark, BookmarkVH>(
    BOOKMARK_DIFF
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkVH =
        BookmarkVH.create(
            parent,
            viewType,
            iconRequest
        )

    override fun onBindViewHolder(holder: BookmarkVH, position: Int) {
        holder.entity = getItem(position)
    }
}

class BookmarkVH(
    itemView: View,
    val iconRequest: (IconRequest) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    var entity: Bookmark? = null
        set(value) {
            field = value
            value?.let { bookmark ->
                itemView.label.text = bookmark.title
                itemView.url.text = bookmark.site?.url

                // try to load icon from webmanifest first
                bookmark.site?.url?.let { url ->
                    iconRequest.invoke(
                        IconRequest(
                            url
                        ) { icon ->
                            if (icon != null) {
                                itemView.favicon.load(icon) {
                                    crossfade(true)
                                    crossfade(400)
                                }
                            } else {
                                itemView.favicon.load(bookmark.site?.favicon) {
                                    crossfade(true)
                                    crossfade(400)
                                }
                            }
                        })
                }
            }
        }

    companion object Factory {
        fun create(parent: ViewGroup, viewType: Int, iconRequest: (IconRequest) -> Unit): BookmarkVH =
            BookmarkVH(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.bookmark_list_item,
                    parent,
                    false
                ), iconRequest
            )
    }
}

val BOOKMARK_DIFF = object : DiffUtil.ItemCallback<Bookmark>() {
    override fun areItemsTheSame(oldItem: Bookmark, newItem: Bookmark): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Bookmark, newItem: Bookmark): Boolean =
        oldItem == newItem
}
