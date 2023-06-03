package ru.iohin.songschords.core.api.di

import android.app.Application

interface ApplicationProvider {
    fun providesApplication(): Application
}