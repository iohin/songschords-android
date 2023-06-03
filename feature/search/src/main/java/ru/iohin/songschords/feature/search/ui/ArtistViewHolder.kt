package ru.iohin.songschords.feature.search.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import ru.iohin.feature.artist.nav.NavigationToArtist
import ru.iohin.songschords.feature.search.R

class ArtistViewHolder(parent: ViewGroup) : ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.artist_item, parent, false)
) {
    private val imageView: ImageView = itemView.findViewById(R.id.artist_image)
    private val nameTextView: TextView = itemView.findViewById(R.id.artist_name)
    var onArtistClick: OnArtistClick? = null

    fun bind(artist: Artist) {
        itemView.setOnClickListener {
            onArtistClick?.onArtistClick(
                itemView,
                nameTextView,
                imageView
            )
        }
        nameTextView.text = artist.name
        ViewCompat.setTransitionName(
            nameTextView,
            "${NavigationToArtist.SHARED_ARTIST_NAME}${artist.id}"
        )
        ViewCompat.setTransitionName(
            imageView,
            "${NavigationToArtist.SHARED_ARTIST_IMAGE}${artist.id}"
        )
        Glide.with(itemView).load(artist.imageUrl).into(imageView)
    }

    fun interface OnArtistClick {
        fun onArtistClick(sharedContainerView: View, sharedNameView: View, sharedImageView: View)
    }
}