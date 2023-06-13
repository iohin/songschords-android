package ru.iohin.songschords.core.data

import ru.iohin.songschords.core.rest.RestSongHitRequest
import ru.iohin.songschords.core.rest.RestService
import ru.iohin.songschords.core.api.entity.Resource
import ru.iohin.songschords.core.api.entity.Result
import ru.iohin.songschords.core.api.entity.SongShort
import ru.iohin.songschords.core.api.data.SongRepository
import java.net.UnknownHostException
import javax.inject.Inject

class SongRepositoryImpl @Inject constructor(private val restService: RestService): SongRepository {
    override suspend fun getArtists(
        searchName: String?,
        offset: Int?,
        limit: Int
    ) = try {
        val response = restService.getArtists(searchName, offset, limit)
        val body = response.body()
        if (response.isSuccessful && body != null) {
            Result.Success(body.toResource { it.toArtistShortList() })
        } else {
            Result.Error(Error(response.message()))
        }
    } catch (e: UnknownHostException) {
        Result.Error(e)
    }

    override suspend fun getArtist(id: Int) = try {
        val response = restService.getArtist(id)
        val body = response.body()
        if (response.isSuccessful && body != null) {
            Result.Success(body.toArtistFull())
        } else {
            Result.Error(Error(response.message()))
        }
    } catch (e: UnknownHostException) {
        Result.Error(e)
    }

    override suspend fun getSongs(
        artistId: Int?,
        searchName: String?,
        searchContent: String?,
        offset: Int?,
        limit: Int
    ): Result<Resource<List<SongShort>>> = try {
        val response = restService.getSongs(artistId, searchName, searchContent, offset, limit)
        val body = response.body()
        if (response.isSuccessful && body != null) {
            Result.Success(body.toResource { it.toSongShortList() })
        } else {
            Result.Error(Error(response.message()))
        }
    } catch (e: UnknownHostException) {
        Result.Error(e)
    }

    override suspend fun getSong(id: Int) = try {
        val response = restService.getSong(id)
        val body = response.body()
        if (response.isSuccessful && body != null) {
            Result.Success(body.toSongFull())
        } else {
            Result.Error(Error(response.message()))
        }
    } catch (e: UnknownHostException) {
        Result.Error(e)
    }

    override suspend fun sendHit(songId: Int) = try {
        val response = restService.sendHit(RestSongHitRequest.build(songId))
        if (response.isSuccessful) {
            Result.Success(Unit)
        } else {
            Result.Error(Error(response.message()))
        }
    } catch (e: UnknownHostException) {
        Result.Error(e)
    }
}