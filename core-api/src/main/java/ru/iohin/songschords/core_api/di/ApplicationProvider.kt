package ru.iohin.songschords.core_api.di

import android.app.Application

interface ApplicationProvider {
    fun providesApplication(): Application
}