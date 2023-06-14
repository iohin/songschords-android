package ru.iohin.songschords.feature.song.ui

import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import ru.iohin.songschords.core.api.entity.SongFull

data class Song(
    val name: String,
    val artistName: String,
    val content: Spannable
) {
    companion object {
        fun from(songFull: SongFull): Song {
            val htmlString = songFull.content
                .replace("[crd]", "<b>")
                .replace("[/crd]", "</b>")
                .replace("\n", "<br>")
                .replace(" ", "&nbsp;")
            val html = Html.fromHtml(htmlString)
            val spannableString = SpannableString(html)
            return Song(
                songFull.name,
                songFull.artistName,
                spannableString
            )
        }
    }
}