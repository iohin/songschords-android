package ru.iohin.songschords.feature.search.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.iohin.songschords.feature.search.R

class SuggestionViewHolder(parent: ViewGroup) : ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.suggestion_item, parent, false)
) {
    private val textView: TextView = itemView.findViewById(R.id.text)

    fun bind(text: String, onClick: (() -> Unit)) {
        itemView.setOnClickListener {
            onClick()
        }
        textView.text = text
    }
}