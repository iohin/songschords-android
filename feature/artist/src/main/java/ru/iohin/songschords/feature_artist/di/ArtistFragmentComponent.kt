package ru.iohin.songschords.feature_artist.di

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Component
import ru.iohin.songschords.core_api.di.ActivityProvider
import ru.iohin.songschords.core_api.di.FragmentScope
import ru.iohin.songschords.core_api.requireActivityProvider
import ru.iohin.songschords.feature_artist.ui.ArtistFragment

@Component(
    dependencies = [
        ActivityProvider::class
    ]
)
@FragmentScope
interface ArtistFragmentComponent: ActivityProvider {
    fun providesFragment(): Fragment
    fun inject(artistFragment: ArtistFragment)

    @Component.Factory
    interface Factory {
        fun create(
            activityProvider: ActivityProvider,
            @BindsInstance
            fragment: Fragment,
        ): ArtistFragmentComponent
    }

    companion object {
        fun getArtistFragmentComponent(fragment: Fragment) =
            DaggerArtistFragmentComponent.factory().create(
                fragment.requireActivity().requireActivityProvider(),
                fragment
            )
    }
}