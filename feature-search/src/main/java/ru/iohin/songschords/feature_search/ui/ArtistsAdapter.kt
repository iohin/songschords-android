package ru.iohin.songschords.feature_search.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter

class ArtistsAdapter: Adapter<ArtistViewHolder>() {
    var artists = emptyList<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ArtistViewHolder(parent)

    override fun getItemCount() = artists.size

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.bind(artists[position])
    }
}