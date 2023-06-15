package ru.iohin.songschords.feature.song.navigation

import android.view.View
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.FragmentNavigatorExtras
import ru.iohin.songschords.core.api.navigation.NavControllerHolder
import ru.iohin.songschords.feature.song.nav.NavigationToSong
import java.net.URLEncoder

class NavigationToSongImpl(private val navControllerHolder: NavControllerHolder): NavigationToSong {
    override fun navigate(
        id: Int,
        name: String,
        artistId: Int,
        artistName: String,
        sharedContainerView: View?,
        sharedNameView: View?,
        sharedArtistNameView: View?
    ) {
        val sharedElements = mutableListOf<Pair<View, String>>()
        sharedNameView?.also {
            sharedElements.add(sharedNameView to "${NavigationToSong.SHARED_NAME}${id}")
        }
        sharedArtistNameView?.also {
            sharedElements.add(sharedArtistNameView to "${NavigationToSong.SHARED_ARTIST_NAME}${artistId}")
        }
        val extras = FragmentNavigatorExtras(*sharedElements.toTypedArray())
        val encodedName = URLEncoder.encode(name, "UTF-8")
        val encodedArtistName = URLEncoder.encode(artistName, "UTF-8")
        val request = NavDeepLinkRequest.Builder
            .fromUri("android-app://ru.iohin.songschords/song_fragment/$id/$encodedName/$artistId/$encodedArtistName".toUri())
            .build()
        navControllerHolder.navController.navigate(request, null, extras)
    }
}