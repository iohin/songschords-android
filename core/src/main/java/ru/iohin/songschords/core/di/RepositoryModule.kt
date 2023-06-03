package ru.iohin.songschords.core.di

import dagger.Binds
import dagger.BindsInstance
import dagger.Module
import ru.iohin.songschords.core.data.SongRepositoryImpl
import ru.iohin.songschords.core.api.data.SongRepository

@Module
interface RepositoryModule {
    @Binds
    fun bindsSongRepository(@BindsInstance songRepository: SongRepositoryImpl): SongRepository
}