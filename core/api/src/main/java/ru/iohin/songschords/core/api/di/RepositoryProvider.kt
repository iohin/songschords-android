package ru.iohin.songschords.core.api.di

import ru.iohin.songschords.core.api.data.SongRepository

interface RepositoryProvider {
    fun providesSongRepository(): SongRepository
}