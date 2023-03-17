package ru.iohin.songschords.core_api.data

import ru.iohin.songschords.core_api.entity.Resource
import ru.iohin.songschords.core_api.entity.SongFull
import ru.iohin.songschords.core_api.entity.SongShort
import ru.iohin.songschords.core_api.entity.Result

interface SongRepository {
    suspend fun getSongs(
        artistId: Int? = null,
        searchName: String? = null,
        searchContent: String? = null
    ): Result<Resource<List<SongShort>>>

    suspend fun getSong(id: Int): Result<SongFull>

    suspend fun sendHit(songId: Int): Result<Unit>
}