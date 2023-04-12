package ru.iohin.songschords.feature_search.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import ru.iohin.songschords.feature_search.R

class ArtistViewHolder(parent: ViewGroup) : ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.artist_item, parent, false)
) {
    private val imageView: ImageView = itemView.findViewById(R.id.artist_image)
    private val nameTextView: TextView = itemView.findViewById(R.id.artist_name)
    var onClick: (() -> Unit)? = null

    fun bind(artist: Artist) {
        itemView.setOnClickListener { onClick?.invoke() }
        nameTextView.text = artist.name
        Glide.with(itemView).load(artist.imageUrl).into(imageView)
    }
}