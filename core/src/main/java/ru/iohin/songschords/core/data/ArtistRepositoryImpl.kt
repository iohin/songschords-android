package ru.iohin.songschords.core.data

import ru.iohin.songschords.core.data.rest.SongsChordsService
import ru.iohin.songschords.core_api.data.ArtistRepository
import ru.iohin.songschords.core_api.entity.ArtistFull
import ru.iohin.songschords.core_api.entity.ArtistShort
import ru.iohin.songschords.core_api.entity.Resource
import ru.iohin.songschords.core_api.entity.Result
import java.net.UnknownHostException
import javax.inject.Inject

class ArtistRepositoryImpl @Inject constructor(private val songsChordsService: SongsChordsService): ArtistRepository {
    override suspend fun getArtists(searchName: String?) = try {
        val response = songsChordsService.getArtists(searchName)
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
        val response = songsChordsService.getArtist(id)
        val body = response.body()
        if (response.isSuccessful && body != null) {
            Result.Success(ArtistFull(
                body.id,
                body.name,
                body.nameAliases.split(","),
                body.description,
                body.imageUrl
            ))
        } else {
            Result.Error(Error(response.message()))
        }
    } catch (e: UnknownHostException) {
        Result.Error(e)
    }
}