package ru.iohin.songschords.feature.search.ui

import ru.iohin.songschords.core.api.entity.ArtistShort

data class Artist(
    val id: Int,
    val name: String,
    val imageUrl: String?
) {
    companion object {
        fun from(artistShort: ArtistShort) = Artist(
            artistShort.id,
            artistShort.name,
            artistShort.imageUrl
        )
    }
}
