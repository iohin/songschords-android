package ru.iohin.feature.artist.nav

import android.view.View

interface NavigationToArtist {
    fun navigate(
        id: Int,
        name: String,
        imageUrl: String?,
        sharedContainerView: View?,
        sharedNameView: View?,
        sharedImageView: View?
    )

    companion object {
        const val SHARED_ARTIST_CONTAINER = "artist_container"
        const val SHARED_ARTIST_NAME = "artist_name"
        const val SHARED_ARTIST_IMAGE = "artist_image"
    }
}