package ru.iohin.songschords.feature.search.ui

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
    var onArtistClick: OnArtistClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ArtistViewHolder(parent)

    override fun getItemCount() = artists.size

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        val artist = artists[position]
        holder.bind(artist)
        holder.onArtistClick =
            ArtistViewHolder.OnArtistClick { sharedContainerView, sharedNameView, sharedImageView ->
                onArtistClick?.onArtistClick(
                    artist,
                    sharedContainerView,
                    sharedNameView,
                    sharedImageView
                )
            }
        if (position == artists.size - 1) {
            onBottomReached?.invoke()
        }
    }

    fun interface OnArtistClick {
        fun onArtistClick(
            artist: Artist,
            sharedContainerView: View,
            sharedNameView: View,
            sharedImageView: View
        )
    }
}