package ru.iohin.songschords.feature.artist.navigation

import android.net.Uri
import android.view.View
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.FragmentNavigatorExtras
import ru.iohin.songschords.feature.artist.nav.NavigationToArtist
import ru.iohin.songschords.feature.artist.nav.NavigationToArtist.Companion.SHARED_ARTIST_IMAGE
import ru.iohin.songschords.feature.artist.nav.NavigationToArtist.Companion.SHARED_ARTIST_NAME
import ru.iohin.songschords.core.api.navigation.NavControllerHolder

class NavigationToArtistImpl(private val navControllerHolder: NavControllerHolder): NavigationToArtist {
    override fun navigate(
        id: Int,
        name: String,
        imageUrl: String?,
        sharedContainerView: View?,
        sharedNameView: View?,
        sharedImageView: View?
    ) {
        val sharedElements = mutableListOf<Pair<View, String>>()
        sharedNameView?.also {
            sharedElements.add(sharedNameView to "${SHARED_ARTIST_NAME}${id}")
        }
        sharedImageView?.also {
            sharedElements.add(sharedImageView to "${SHARED_ARTIST_IMAGE}${id}")
        }
        val extras = FragmentNavigatorExtras(*sharedElements.toTypedArray())

        val encodedName = Uri.encode(name)
        val encodedImageUrl = Uri.encode(imageUrl)
        val request = NavDeepLinkRequest.Builder
            .fromUri("android-app://ru.iohin.songschords/artist_fragment/$id/$encodedName/$encodedImageUrl".toUri())
            .build()
        navControllerHolder.navController.navigate(request, null, extras)
    }
}