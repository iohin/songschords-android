package ru.iohin.songschords.feature.song.nav

import android.view.View

interface NavigationToSong {
    fun navigate(
        id: Int,
        name: String,
        artistName: String,
        sharedContainerView: View?,
        sharedNameView: View?,
        sharedArtistNameView: View?
    )

    companion object {
        const val SHARED_CONTAINER = "song_container"
        const val SHARED_ARTIST_NAME = "artist_name"
        const val SHARED_NAME = "song_name"
    }
}