package ru.iohin.songschords.di

import android.app.Activity
import androidx.navigation.NavController
import dagger.BindsInstance
import dagger.Component
import ru.iohin.songschords.core_api.di.ActivityProvider
import ru.iohin.songschords.core_api.di.ActivityScope
import ru.iohin.songschords.core_api.di.CoreProvider
import ru.iohin.songschords.core_api.requireCoreProvider
import ru.iohin.songschords.navigation.NavControllerHolder
import ru.iohin.songschords.ui.MainActivity

@Component(
    modules = [
        NavigationModule::class
    ],
    dependencies = [
        CoreProvider::class
    ]
)
@ActivityScope
interface MainActivityComponent: ActivityProvider {

    @Component.Factory
    interface Factory {
        fun create(
            coreProvider: CoreProvider,
            @BindsInstance
            activity: Activity,
            navigationModule: NavigationModule
        ): MainActivityComponent
    }

    companion object {
        fun getMainActivityComponent(mainActivity: MainActivity) =
            DaggerMainActivityComponent.factory()
                .create(
                    mainActivity.application.requireCoreProvider(),
                    mainActivity,
                    NavigationModule(mainActivity)
                )
    }
}