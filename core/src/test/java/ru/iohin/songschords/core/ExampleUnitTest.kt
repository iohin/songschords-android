package ru.iohin.songschords.core

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import org.junit.Test

import retrofit2.Retrofit
import ru.iohin.songschords.core_api.entity.Result
import ru.iohin.songschords.core.data.ArtistRepositoryImpl
import ru.iohin.songschords.core.data.SongRepositoryImpl
import ru.iohin.songschords.core.data.rest.AuthInterceptor
import ru.iohin.songschords.core.data.rest.SongsChordsService
import ru.iohin.songschords.core_api.entity.Resource
import java.util.concurrent.TimeUnit

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun testRepositories() {
        runBlocking {
            val client = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(AuthInterceptor())
                .build()
            val contentType = MediaType.parse("application/json")!!
            val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl("${BuildConfig.REST_URL}${SongsChordsService.API_PATH}")
                .addConverterFactory(Json.asConverterFactory(contentType))
                .build()
            val artistRepository = ArtistRepositoryImpl(retrofit.create(SongsChordsService::class.java))
            val songRepository = SongRepositoryImpl(retrofit.create(SongsChordsService::class.java))

            val artists = artistRepository.getArtists()
            val searchArtists = artistRepository.getArtists("чиж")
            val songs = songRepository.getSongs()
            val searchSongsArtist = songRepository.getSongs(artistId = 3)
            val searchSongsName = songRepository.getSongs(searchName = "о любви")
            val searchSongsContent = songRepository.getSongs(searchContent = "я сижу и смотрю")
            val artist = artistRepository.getArtist(1)
            val song = songRepository.getSong(1)

            assert(artists is Result.Success && isResultDataValid(artists))
            assert(searchArtists is Result.Success && isResultDataValid(searchArtists))
            assert(songs is Result.Success && isResultDataValid(songs))
            assert(searchSongsArtist is Result.Success && isResultDataValid(searchSongsArtist))
            assert(searchSongsName is Result.Success && isResultDataValid(searchSongsName))
            assert(searchSongsContent is Result.Success && isResultDataValid(searchSongsContent))
            assert(artist is Result.Success)
            assert(song is Result.Success)

            val testSongs = songRepository.getSongs(searchName = "test")
            if (testSongs is Result.Success) {
                testSongs.data.data.find { it.name == "test" }?.let {
                    val hit = songRepository.sendHit(it.id)
                    assert(hit is Result.Success)
                }
            }
        }
    }

    private fun <T: Any>isResultDataValid(result: Result<Resource<List<T>>>) =
        result is Result.Success
                && (result.data.totalCount > result.data.limit
                && result.data.data.size == result.data.limit
                || result.data.totalCount < result.data.limit
                && result.data.data.size == result.data.totalCount)
}