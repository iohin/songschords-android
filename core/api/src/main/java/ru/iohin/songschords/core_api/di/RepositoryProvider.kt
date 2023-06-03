package ru.iohin.songschords.core_api.di

import ru.iohin.songschords.core_api.data.SongRepository

interface RepositoryProvider {
    fun providesSongRepository(): SongRepository
}