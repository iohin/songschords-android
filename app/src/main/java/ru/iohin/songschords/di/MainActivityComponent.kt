package ru.iohin.songschords.di

import android.app.Activity
import dagger.BindsInstance
import dagger.Component
import ru.iohin.songschords.core_api.di.ActivityProvider
import ru.iohin.songschords.core_api.di.CoreProvider
import ru.iohin.songschords.core_api.requireCoreProvider

@Component(
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
        ): MainActivityComponent
    }

    companion object {
        fun getMainActivityComponent(activity: Activity) =
            DaggerMainActivityComponent.factory()
                .create(
                    activity.application.requireCoreProvider(),
                    activity
                )
    }
}