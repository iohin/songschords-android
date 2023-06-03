package ru.iohin.songschords.feature.artist.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import ru.iohin.songschords.feature.artist.R
import ru.iohin.songschords.feature.song.nav.NavigationToSong

class SongViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.song_item, parent, false)
) {
    private val nameTextView: TextView = itemView.findViewById(R.id.song_name)
    var onSongClick: OnSongClick? = null

    fun bind(song: Artist.Song) {
        itemView.setOnClickListener {
            onSongClick?.onSongClick(nameTextView)
        }
        nameTextView.text = song.name
        ViewCompat.setTransitionName(
            nameTextView,
            "${NavigationToSong.SHARED_NAME}${song.id}"
        )
    }

    fun interface OnSongClick {
        fun onSongClick(sharedNameView: View)
    }
}