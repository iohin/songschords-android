package ru.iohin.songschords.feature.song.ui

import ru.iohin.songschords.core.api.entity.SongFull

data class Song(
    val name: String,
    val artistName: String,
    val chords: List<String>,
    val content: String
) {
    companion object {
        fun from(songFull: SongFull) = Song(
            songFull.name,
            songFull.artistName,
            songFull.getChords(),
            songFull.content
        )
    }
}