package ru.iohin.songschords.feature_search.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.iohin.songschords.feature_search.R

class ArtistViewHolder(parent: ViewGroup) : ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.artist_item, parent, false)
) {
    val nameTextView: TextView = itemView.findViewById(R.id.artist_name)

    fun bind(name: String) {
        nameTextView.text = name
    }
}