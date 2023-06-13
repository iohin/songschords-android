package ru.iohin.songschords.feature.song.ui

import ru.iohin.songschords.core.api.entity.SongFull

data class Song(
    val name: String,
    val artistName: String,
    val content: String
) {
    companion object {
        fun from(songFull: SongFull) = Song(
            songFull.name,
            songFull.artistName,
            songFull.content.replace("[crd]", "").replace("[/crd]", "")
        )
    }
}