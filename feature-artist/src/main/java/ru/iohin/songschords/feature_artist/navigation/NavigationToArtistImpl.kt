package ru.iohin.songschords.feature_artist.navigation

import android.view.View
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.FragmentNavigatorExtras
import ru.iohin.feature.artist.nav.NavigationToArtist
import ru.iohin.songschords.core_api.navigation.NavControllerHolder
import java.net.URLEncoder

class NavigationToArtistImpl(private val navControllerHolder: NavControllerHolder): NavigationToArtist {
    override fun navigate(
        id: Int,
        name: String,
        imageUrl: String?,
        containerView: View?,
        nameView: View?,
        imageView: View?
    ) {
        val sharedElements = mutableListOf<Pair<View, String>>()
//        containerView?.also {
//            sharedElements.add(containerView to "artist_container${id}")
//        }
        nameView?.also {
            sharedElements.add(nameView to "artist_name${id}")
        }
        imageView?.also {
            sharedElements.add(imageView to "artist_image${id}")
        }
        val extras = FragmentNavigatorExtras(*sharedElements.toTypedArray())

        val encodedName = URLEncoder.encode(name, "UTF-8")
        val encodedImageUrl = URLEncoder.encode(imageUrl, "UTF-8")
        val request = NavDeepLinkRequest.Builder
            .fromUri("android-app://ru.iohin.songschords/artist_fragment/$id/$encodedName/$encodedImageUrl".toUri())
            .build()
        navControllerHolder.navController.navigate(request, null, extras)
    }
}