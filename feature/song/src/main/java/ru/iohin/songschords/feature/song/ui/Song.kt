package ru.iohin.songschords.feature.song.ui

import ru.iohin.songschords.core.api.entity.SongFull

data class Song(
    val name: String,
    val artistName: String,
    val chords: List<String>,
    val content: String
) {
    companion object {
        fun getChords(content: String) =
            """\[crd]([^\[]+)\[/crd]"""
                .toRegex()
                .findAll(content)
                .map { it.groupValues[1] }
                .distinct()
                .toList()

        fun from(songFull: SongFull) = Song(
            songFull.name,
            songFull.artistName,
            getChords(songFull.content),
            songFull.content
        )
    }
}