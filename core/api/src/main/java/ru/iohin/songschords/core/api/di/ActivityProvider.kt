package ru.iohin.songschords.core.api.di

import android.app.Activity

interface ActivityProvider: CoreProvider, NavigationProvider {
    fun providesActivity(): Activity
}