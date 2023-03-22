package ru.iohin.songschords.core_api.data

import ru.iohin.songschords.core_api.entity.*

interface SongRepository {
    suspend fun getArtists(
        searchName: String? = null,
        offset: Int? = null,
        limit: Int = 20
    ): Result<Resource<List<ArtistShort>>>

    suspend fun getArtist(id: Int): Result<ArtistFull>

    suspend fun getSongs(
        artistId: Int? = null,
        searchName: String? = null,
        searchContent: String? = null,
        offset: Int? = null,
        limit: Int = 20
    ): Result<Resource<List<SongShort>>>

    suspend fun getSong(id: Int): Result<SongFull>

    suspend fun sendHit(songId: Int): Result<Unit>
}