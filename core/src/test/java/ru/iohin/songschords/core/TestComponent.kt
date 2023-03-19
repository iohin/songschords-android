package ru.iohin.songschords.core

import dagger.Component
import ru.iohin.songschords.core.di.RepositoryModule
import ru.iohin.songschords.core.di.RestModule

@Component(
    modules = [
        RestModule::class,
        RepositoryModule::class
    ]
)
interface TestCoreComponent {
    fun inject(exampleUnitTest: RepositoryUnitTest)
}