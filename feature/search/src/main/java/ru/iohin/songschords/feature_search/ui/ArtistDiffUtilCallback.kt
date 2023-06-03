package ru.iohin.songschords.feature_search.ui

import androidx.recyclerview.widget.DiffUtil

class ArtistDiffUtilCallback(
    private val oldItems: List<Artist>,
    private val newItems: List<Artist>
): DiffUtil.Callback() {
    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldItems[oldItemPosition].id == newItems[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldItems[oldItemPosition] == newItems[newItemPosition]
}