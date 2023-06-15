package ru.iohin.songschords.feature.song.ui

import android.text.Html
import android.text.SpannableString

object ContentConvertor {
    fun convert(content: String): CharSequence = SpannableString(
        Html.fromHtml(
            content
                .replace("[crd]", "<b>")
                .replace("[/crd]", "</b>")
                .replace("\n", "<br>")
                .replace(" ", "&nbsp;")
        )
    )
}