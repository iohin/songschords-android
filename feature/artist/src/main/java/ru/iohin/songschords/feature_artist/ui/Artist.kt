package ru.iohin.songschords.feature_artist.ui

data class Artist(
    val id: Int = 0,
    val name: String = "",
    val imageUrl: String? = null,
    val description: String = "",
    val songs: List<Song> = emptyList(),
) {
    data class Song(
        val id: Int,
        val name: String,
    )
}
