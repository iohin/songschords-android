package ru.iohin.songschords.feature.artist.ui

import androidx.recyclerview.widget.DiffUtil

class SongsDiffUtilCallback(
    private val oldItems: List<Artist.Song>,
    private val newItems: List<Artist.Song>,
): DiffUtil.Callback() {
    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldItems[oldItemPosition].id == newItems[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldItems[oldItemPosition] == newItems[newItemPosition]
}