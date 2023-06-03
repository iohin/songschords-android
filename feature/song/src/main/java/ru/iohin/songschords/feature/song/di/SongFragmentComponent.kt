package ru.iohin.songschords.feature.song.di

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Component
import ru.iohin.songschords.core.api.di.ActivityProvider
import ru.iohin.songschords.core.api.di.FragmentScope
import ru.iohin.songschords.core.api.requireActivityProvider
import ru.iohin.songschords.feature.song.ui.SongFragment

@Component(
    dependencies = [
        ActivityProvider::class
    ]
)
@FragmentScope
interface SongFragmentComponent: ActivityProvider {
    fun providesFragment(): Fragment
    fun inject(songFragment: SongFragment)

    @Component.Factory
    interface Factory {
        fun create(
            activityProvider: ActivityProvider,
            @BindsInstance
            fragment: Fragment,
        ): SongFragmentComponent
    }

    companion object {
        fun getSongFragmentComponent(fragment: Fragment) =
            DaggerSongFragmentComponent.factory().create(
                fragment.requireActivity().requireActivityProvider(),
                fragment
            )
    }
}