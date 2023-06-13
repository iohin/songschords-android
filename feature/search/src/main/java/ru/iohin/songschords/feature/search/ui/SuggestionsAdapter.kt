package ru.iohin.songschords.feature.search.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter

class SuggestionsAdapter: Adapter<SuggestionViewHolder>() {
    var suggestions: List<String> = listOf()
        set(value) {
            DiffUtil.calculateDiff(
                SuggestionsDiffUtilCallback(
                    field,
                    value
                )
            ).dispatchUpdatesTo(this)
            field = value
        }
    var onItemClick: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SuggestionViewHolder(parent)

    override fun getItemCount() = suggestions.size

    override fun onBindViewHolder(holder: SuggestionViewHolder, position: Int) {
        val text = suggestions[position]
        holder.bind(text) {
            onItemClick?.invoke(text)
        }
    }
}