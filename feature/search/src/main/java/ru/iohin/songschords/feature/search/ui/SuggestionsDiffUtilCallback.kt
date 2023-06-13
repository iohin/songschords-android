package ru.iohin.songschords.feature.search.ui

import androidx.recyclerview.widget.DiffUtil

class SuggestionsDiffUtilCallback(
    private val oldItems: List<String>,
    private val newItems: List<String>
): DiffUtil.Callback() {
    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldItems[oldItemPosition] == newItems[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldItems[oldItemPosition] == newItems[newItemPosition]
}