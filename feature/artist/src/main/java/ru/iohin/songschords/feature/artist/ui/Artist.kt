package ru.iohin.songschords.feature.artist.ui

import ru.iohin.songschords.core.api.entity.ArtistFull
import ru.iohin.songschords.core.api.entity.SongShort

data class Artist(
    val id: Int = 0,
    val name: String = "",
    val imageUrl: String? = null,
    val description: String = "",
    val songs: List<Song> = emptyList(),
) {
    companion object {
        fun from(artistFull: ArtistFull, songs: List<Song>) = Artist(
            artistFull.id,
            artistFull.name,
            artistFull.imageUrl,
            artistFull.description,
            songs
        )
    }
    data class Song(
        val id: Int,
        val name: String,
    ) {
        companion object {
            fun from(songShort: SongShort) = Song(
                songShort.id,
                songShort.name
            )
        }
    }
}
