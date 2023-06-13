package ru.iohin.songschords.core

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Test
import org.junit.Assert.*

import ru.iohin.songschords.core.api.entity.Result
import ru.iohin.songschords.core.api.data.SongRepository
import ru.iohin.songschords.core.api.entity.ArtistFull
import ru.iohin.songschords.core.api.entity.ArtistShort
import ru.iohin.songschords.core.api.entity.Resource
import ru.iohin.songschords.core.api.entity.SongFull
import ru.iohin.songschords.core.api.entity.SongShort
import javax.inject.Inject
class RepositoryUnitTest {
    @Inject
    lateinit var mockWebServer: MockWebServer
    @Inject
    lateinit var songRepository: SongRepository

    init {
        DaggerTestCoreComponent.create().inject(this)
    }

    @Test
    fun `should get artists`() {
        runBlocking {
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(ResourceHelper.getJson("json/artists.json"))
            )

            val expected = Result.Success(
                Resource(2, 20, 0,
                    listOf(
                        ArtistShort(
                            id = 1,
                            name = "Test Artist 1",
                            imageUrl = "https://songschords/images/1.jpg"
                        ),
                        ArtistShort(
                            id = 2,
                            name = "Test Artist 2",
                            imageUrl = "https://songschords/images/2.jpg"
                        )
                    )
                )
            )

            val actual = songRepository.getArtists() as Result.Success<Resource<List<ArtistShort>>>

            assertEquals(expected, actual)
        }
    }

    @Test
    fun `should get artist with id 1`() {
        runBlocking {
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(ResourceHelper.getJson("json/artist_1.json"))
            )

            val expected = Result.Success(
                ArtistFull(
                    id = 1,
                    name = "Test Artist 1",
                    nameAliases = listOf("Test Artist Alias 1"),
                    description = "Test Artist Description 1",
                    imageUrl = "https://songschords/images/1.jpg"
                )
            )

            val actual = songRepository.getArtist(1) as Result.Success<ArtistFull>

            assertEquals(expected, actual)
        }
    }

    @Test
    fun `should get songs`() {
        runBlocking {
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(ResourceHelper.getJson("json/songs.json"))
            )

            val expected = Result.Success(
                Resource(2, 20, 0,
                    listOf(
                        SongShort(
                            id = 1,
                            name = "Song 1",
                            artistId = 1,
                            artistName = "Artist 1"
                        ),
                        SongShort(
                            id = 2,
                            name = "Song 2",
                            artistId = 2,
                            artistName = "Artist 2"
                        )
                    )
                )
            )

            val actual = songRepository.getSongs() as Result.Success<Resource<List<SongShort>>>

            assertEquals(expected, actual)
        }
    }

    @Test
    fun `should get song with id 1`() {
        runBlocking {
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(ResourceHelper.getJson("json/song_1.json"))
            )

            val expected = Result.Success(
                SongFull(
                    artistId = 1,
                    artistName = "Artist 1",
                    author = "Author 1",
                    content = "Content 1",
                    copyright = "Copyright 1",
                    id = 1,
                    name = "Song 1",
                )
            )

            val actual = songRepository.getSong(1) as Result.Success<SongFull>

            assertEquals(expected, actual)
        }
    }

    @Test
    fun `should send hit for song 1`() {
        runBlocking {
            val expectedBody = """{"song":"/api/v1/song/1/"}"""

            mockWebServer.dispatcher = object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse {
                    return if (request.body.readUtf8() == expectedBody) {
                        MockResponse()
                            .setResponseCode(200)
                    } else {
                        MockResponse()
                            .setResponseCode(500)
                    }
                }
            }

            val expected = Result.Success(Unit)
            val actual = songRepository.sendHit(1) as Result.Success

            assertEquals(expected, actual)
        }
    }

    @Test
    fun `should send hit failure`() {
        runBlocking {
            val expectedBody = """{"song":"/api/v1/song/1/"}"""

            mockWebServer.dispatcher = object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse {
                    return if (request.body.readUtf8() == expectedBody) {
                        MockResponse()
                            .setResponseCode(200)
                    } else {
                        MockResponse()
                            .setResponseCode(500)
                    }
                }
            }

            val expected = Result.Error(Error("Server Error")).exception.message
            val actual = (songRepository.sendHit(2) as Result.Error).exception.message

            assertEquals(expected, actual)
        }
    }

    @Test
    fun `should error`() {
        runBlocking {
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(404)
            )

            val expected = Result.Error(Error("Client Error")).exception.message
            val actual = (songRepository.getArtist(3) as Result.Error).exception.message

            assertEquals(expected, actual)
        }
    }
}