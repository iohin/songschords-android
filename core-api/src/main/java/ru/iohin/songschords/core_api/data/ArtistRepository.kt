package ru.iohin.songschords.core_api.data

import ru.iohin.songschords.core_api.entity.ArtistFull
import ru.iohin.songschords.core_api.entity.ArtistShort
import ru.iohin.songschords.core_api.entity.Resource
import ru.iohin.songschords.core_api.entity.Result

interface ArtistRepository {
    suspend fun getArtists(searchName: String? = null): Result<Resource<List<ArtistShort>>>

    suspend fun getArtist(id: Int): Result<ArtistFull>
}