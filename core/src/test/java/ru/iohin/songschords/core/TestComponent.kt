package ru.iohin.songschords.core

import dagger.Component
import ru.iohin.songschords.core.di.RepositoryModule
import javax.inject.Singleton

@Component(
    modules = [
        TestRestModule::class,
        RepositoryModule::class
    ]
)
@Singleton
interface TestCoreComponent {
    fun inject(exampleUnitTest: RepositoryUnitTest)
    fun inject(restServiceUnitTest: RestServiceUnitTest)
}