package ru.iohin.songschords.core

import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

import ru.iohin.songschords.core.api.entity.Result
import ru.iohin.songschords.core.api.data.SongRepository
import ru.iohin.songschords.core.api.entity.Resource
import javax.inject.Inject

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class RepositoryUnitTest {
    @Inject
    lateinit var songRepository: SongRepository

    @Before
    fun createTestComponent() {
        DaggerTestCoreComponent.create().inject(this)
    }

    @Test
    @Inject
    fun testRepository() {
        runBlocking {
            val artists = songRepository.getArtists()
            val searchArtists = songRepository.getArtists("чиж")
            val songs = songRepository.getSongs()
            val searchSongsArtist = songRepository.getSongs(artistId = 3)
            val searchSongsName = songRepository.getSongs(searchName = "о любви")
            val searchSongsContent = songRepository.getSongs(searchContent = "я сижу и смотрю")
            val artist = songRepository.getArtist(1)
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