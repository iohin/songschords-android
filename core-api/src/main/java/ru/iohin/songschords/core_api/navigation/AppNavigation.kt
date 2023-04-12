package ru.iohin.songschords.core_api.navigation

interface AppNavigation {
    fun openArtist(id: Int, name: String, imageUrl: String?)
}