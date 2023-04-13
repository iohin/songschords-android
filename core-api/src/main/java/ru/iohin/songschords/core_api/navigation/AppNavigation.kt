package ru.iohin.songschords.core_api.navigation

import android.view.View

interface AppNavigation {
    fun openArtist(id: Int, name: String, imageUrl: String?, nameView: View, imageView: View)
}