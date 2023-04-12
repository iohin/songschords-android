package ru.iohin.songschords.navigation

import androidx.navigation.NavController
import ru.iohin.songschords.core_api.navigation.AppNavigation
import ru.iohin.songschords.feature_search.ui.SearchFragmentDirections

class AppNavigationImpl(private val navController: NavController): AppNavigation {
    override fun openArtist(id: Int, name: String, imageUrl: String?) {
        navController.navigate(
            SearchFragmentDirections.actionSearchFragmentToArtistFragment(
                id, name, imageUrl ?: ""
            )
        )
    }
}