package ru.iohin.songschords.core.data

import ru.iohin.songschords.core_api.rest.RestSongHitRequest
import ru.iohin.songschords.core_api.rest.RestService
import ru.iohin.songschords.core_api.data.SongRepository
import ru.iohin.songschords.core_api.entity.*
import java.net.UnknownHostException
import javax.inject.Inject

class SongRepositoryImpl @Inject constructor(private val restService: RestService): SongRepository {
    override suspend fun getArtists(searchName: String?) = try {
        val response = restService.getArtists(searchName)
        val body = response.body()
        if (response.isSuccessful && body != null) {
            Result.Success(Resource(
                totalCount = body.meta.totalCount,
                limit = body.meta.limit,
                offset = body.meta.offset,
                data = body.objects.map { ArtistShort(
                    id = it.id,
                    name = it.name
                ) }
            ))
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
            Result.Success(
                ArtistFull(
                body.id,
                body.name,
                body.nameAliases.split(","),
                body.description,
                body.imageUrl
            )
            )
        } else {
            Result.Error(Error(response.message()))
        }
    } catch (e: UnknownHostException) {
        Result.Error(e)
    }

    override suspend fun getSongs(
        artistId: Int?,
        searchName: String?,
        searchContent: String?
    ): Result<Resource<List<SongShort>>> = try {
        val response = restService.getSongs(artistId, searchName, searchContent)
        val body = response.body()
        if (response.isSuccessful && body != null) {
            Result.Success(Resource(
                totalCount = body.meta.totalCount,
                limit = body.meta.limit,
                offset = body.meta.offset,
                data = body.objects.map { SongShort(
                    it.id,
                    it.name,
                    it.artist,
                    it.artistName
                ) }
            ))
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
            Result.Success(SongFull(
                body.id,
                body.name,
                body.artist,
                body.artistName,
                body.author,
                body.content,
                body.copyright
            ))
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