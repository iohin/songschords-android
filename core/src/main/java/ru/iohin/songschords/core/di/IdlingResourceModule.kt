package ru.iohin.songschords.core.di

import androidx.test.espresso.idling.CountingIdlingResource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class IdlingResourceModule {

    @Provides
    @Singleton
    fun provideIdlingResource() = CountingIdlingResource("test_resource")
}