package ru.iohin.songschords.feature_search.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter

class ArtistsAdapter: Adapter<ArtistViewHolder>() {
    var artists = emptyList<Artist>()
        set(value) {
            DiffUtil.calculateDiff(
                ArtistDiffUtilCallback(
                    field,
                    value
                )
            ).dispatchUpdatesTo(this)
            field = value
        }

    var onBottomReached: (() -> Unit)? = null
    var onArtistClick: ((Artist, View, View, View) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ArtistViewHolder(parent)

    override fun getItemCount() = artists.size

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        val artist = artists[position]
        holder.bind(artist)
        holder.onClick = { containerView, nameView, imageView -> onArtistClick?.invoke(artist, containerView, nameView, imageView) }
        if (position == artists.size - 1) {
            onBottomReached?.invoke()
        }
    }
}