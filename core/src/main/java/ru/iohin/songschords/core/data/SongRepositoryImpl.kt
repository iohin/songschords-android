package ru.iohin.songschords.core.data

import ru.iohin.songschords.core.data.rest.RestSongHitRequest
import ru.iohin.songschords.core.data.rest.SongsChordsService
import ru.iohin.songschords.core_api.data.SongRepository
import ru.iohin.songschords.core_api.entity.Resource
import ru.iohin.songschords.core_api.entity.Result
import ru.iohin.songschords.core_api.entity.SongFull
import ru.iohin.songschords.core_api.entity.SongShort
import java.net.UnknownHostException
import javax.inject.Inject

class SongRepositoryImpl @Inject constructor(private val songsChordsService: SongsChordsService): SongRepository {
    override suspend fun getSongs(
        artistId: Int?,
        searchName: String?,
        searchContent: String?
    ): Result<Resource<List<SongShort>>> = try {
        val response = songsChordsService.getSongs(artistId, searchName, searchContent)
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
        val response = songsChordsService.getSong(id)
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
        val response = songsChordsService.sendHit(RestSongHitRequest.build(songId))
        if (response.isSuccessful) {
            Result.Success(Unit)
        } else {
            Result.Error(Error(response.message()))
        }
    } catch (e: UnknownHostException) {
        Result.Error(e)
    }
}