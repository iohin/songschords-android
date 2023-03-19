package ru.iohin.songschords.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.iohin.songschords.core.di.RepositoryModule
import ru.iohin.songschords.core.di.RestModule
import ru.iohin.songschords.core_api.di.CoreProvider

@Component(
    modules = [
        RestModule::class,
        RepositoryModule::class
    ]
)
interface CoreComponent: CoreProvider {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): CoreComponent
    }
}