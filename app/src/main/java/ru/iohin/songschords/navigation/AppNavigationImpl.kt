package ru.iohin.songschords.navigation

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import ru.iohin.songschords.core_api.navigation.AppNavigation
import ru.iohin.songschords.feature_search.ui.SearchFragmentDirections

class AppNavigationImpl(private val navController: NavController): AppNavigation {
    override fun openArtist(id: Int, name: String, imageUrl: String?, nameView: View, imageView: View) {
        val extras = FragmentNavigatorExtras(
            nameView to "artist_name${id}",
            imageView to "artist_image${id}"
        )
        val dest = SearchFragmentDirections.actionSearchFragmentToArtistFragment(
            id, name, imageUrl ?: ""
        )
        navController.navigate(
            dest,
            extras
        )
    }
}