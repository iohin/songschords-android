package ru.iohin.songschords.core.api.di

import android.app.Application
import androidx.test.espresso.idling.CountingIdlingResource

interface ApplicationProvider {
    fun providesApplication(): Application
    fun provideIdlingResource(): CountingIdlingResource
}