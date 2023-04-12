package ru.iohin.songschords.feature_search.ui

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ArtistViewHolder(parent)

    override fun getItemCount() = artists.size

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.bind(artists[position])
        if (position == artists.size - 1) {
            onBottomReached?.invoke()
        }
    }
}