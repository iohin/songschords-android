package ru.iohin.feature.artist.nav

import android.view.View

interface NavigationToArtist {
    fun navigate(id: Int, name: String, imageUrl: String?, containerView: View?, nameView: View?, imageView: View?)
}