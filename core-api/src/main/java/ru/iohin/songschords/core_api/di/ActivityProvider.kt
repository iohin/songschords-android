package ru.iohin.songschords.core_api.di

import android.app.Activity

interface ActivityProvider: CoreProvider, NavigationProvider {
    fun providesActivity(): Activity
}