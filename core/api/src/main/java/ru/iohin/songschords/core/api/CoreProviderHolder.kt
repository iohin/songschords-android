package ru.iohin.songschords.core.api

import ru.iohin.songschords.core.api.di.CoreProvider

interface CoreProviderHolder {
    val coreProvider: CoreProvider
}