package ru.iohin.songschords.core_api

import ru.iohin.songschords.core_api.di.CoreProvider

interface CoreProviderHolder {
    val coreProvider: CoreProvider
}