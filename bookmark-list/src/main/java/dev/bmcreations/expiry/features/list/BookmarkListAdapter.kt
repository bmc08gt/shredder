package dev.bmcreations.expiry.features.list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import dev.bmcreations.expiry.models.Bookmark
import kotlinx.android.synthetic.main.bookmark_list_item.view.*

class BookmarkListAdapter : ListAdapter<Bookmark, BookmarkVH>(BOOKMARK_DIFF) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkVH =
        BookmarkVH.create(parent, viewType)

    override fun onBindViewHolder(holder: BookmarkVH, position: Int) {
        holder.entity = getItem(position)
    }
}

class BookmarkVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var entity: Bookmark? = null
        set(value) {
            field = value
            value?.let { bookmark ->
                itemView.label.text = bookmark.label
                itemView.url.text = bookmark.url

                itemView.favicon.load(bookmark.favicon) {
                   listener(onError = { _, error ->
                       error.printStackTrace()
                   })
                }
            }
        }

    companion object Factory {
        fun create(parent: ViewGroup, viewType: Int): BookmarkVH = BookmarkVH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.bookmark_list_item,
                parent,
                false
            )
        )
    }
}

val BOOKMARK_DIFF = object : DiffUtil.ItemCallback<Bookmark>() {
    override fun areItemsTheSame(oldItem: Bookmark, newItem: Bookmark): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Bookmark, newItem: Bookmark): Boolean =
        oldItem == newItem
}
