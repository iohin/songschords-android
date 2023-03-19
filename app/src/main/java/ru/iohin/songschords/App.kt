package ru.iohin.songschords

import android.app.Application
import ru.iohin.songschords.core_api.CoreProviderHolder
import ru.iohin.songschords.core_api.di.CoreProvider
import ru.iohin.songschords.di.CoreComponent

import ru.iohin.songschords.di.DaggerCoreComponent

class App: Application(), CoreProviderHolder {
    lateinit var coreComponent: CoreComponent
    override val coreProvider: CoreProvider
        get() = coreComponent

    override fun onCreate() {
        super.onCreate()
        coreComponent = DaggerCoreComponent.factory().create(this)
    }
}