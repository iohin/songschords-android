package ru.iohin.songschords.feature.song

import android.app.Application

class TestApp: Application() {
    override fun onCreate() {
        super.onCreate()
        setTheme(androidx.appcompat.R.style.Theme_AppCompat)
    }
}