package ru.iohin.songschords.feature.artist.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter

class SongsAdapter: Adapter<SongViewHolder>() {
    var songs = emptyList<Artist.Song>()
        set(value) {
            DiffUtil.calculateDiff(SongsDiffUtilCallback(field, value)).dispatchUpdatesTo(this)
            field = value
        }

    var onBottomReached: (() -> Unit)? = null
    var onSongClick: OnSongClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SongViewHolder(parent)

    override fun getItemCount() = songs.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]
        holder.bind(song)
        holder.onSongClick = SongViewHolder.OnSongClick { sharedNameView ->
            onSongClick?.onSongClick(song, sharedNameView)
        }
        if (position == songs.size - 1) {
            onBottomReached?.invoke()
        }
    }

    fun interface OnSongClick {
        fun onSongClick(song: Artist.Song, sharedNameView: View)
    }
}