package ru.iohin.songschords.feature_search.di

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Component
import ru.iohin.songschords.core_api.di.ActivityProvider
import ru.iohin.songschords.core_api.di.FragmentScope
import ru.iohin.songschords.core_api.requireActivityProvider
import ru.iohin.songschords.feature_search.ui.SearchFragment

@Component(
    dependencies = [
        ActivityProvider::class
    ]
)
@FragmentScope
interface SearchFragmentComponent: ActivityProvider {
    fun providesFragment(): Fragment
    fun inject(searchFragment: SearchFragment)

    @Component.Factory
    interface Factory {
        fun create(
            activityProvider: ActivityProvider,
            @BindsInstance
            fragment: Fragment,
        ): SearchFragmentComponent
    }

    companion object {
        fun getSearchFragmentComponent(fragment: Fragment) =
            DaggerSearchFragmentComponent.factory()
                .create(
                    fragment.requireActivity().requireActivityProvider(),
                    fragment
                )
    }
}