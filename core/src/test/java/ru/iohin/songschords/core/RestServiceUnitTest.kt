package ru.iohin.songschords.core

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.iohin.songschords.core.ResourceHelper.getJson
import ru.iohin.songschords.core.rest.RestArtistFull
import ru.iohin.songschords.core.rest.RestArtistShort
import ru.iohin.songschords.core.rest.RestResource
import ru.iohin.songschords.core.rest.RestService
import ru.iohin.songschords.core.rest.RestSongFull
import ru.iohin.songschords.core.rest.RestSongHitRequest
import ru.iohin.songschords.core.rest.RestSongShort
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class RestServiceUnitTest {
    @Inject
    lateinit var mockWebServer: MockWebServer
    @Inject
    lateinit var restService: RestService

    init {
        DaggerTestCoreComponent.create().inject(this)
    }

    @Before
    fun startServer() {
        try {
            mockWebServer.start()
        } catch (ignored: Throwable) {}
    }

    @After
    fun stopServer() {
        mockWebServer.shutdown()
    }

    @Test
    fun `should load artists`() {
        runBlocking {
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(getJson("json/artists.json"))
            )

            val expectedResponse = RestResource(
                meta = RestResource.Meta(
                    limit = 20,
                    next = null,
                    offset = 0,
                    previous = null,
                    totalCount = 2
                ),
                objects = listOf(
                    RestArtistShort(
                        id = 1,
                        imageUrl = "https://songschords/images/1.jpg",
                        name = "Test Artist 1",
                        nameAliases = "Test Artist Alias 1",
                        resourceUri = "/api/v1/artist/1/"
                    ),
                    RestArtistShort(
                        id = 2,
                        imageUrl = "https://songschords/images/2.jpg",
                        name = "Test Artist 2",
                        nameAliases = "Test Artist Alias 2",
                        resourceUri = "/api/v1/artist/2/"
                    )
                )
            )

            val actualResponse = restService.getArtists().body()

            assertEquals(expectedResponse, actualResponse)
        }
    }

    @Test
    fun `should load artist with id 1`() {
        runBlocking {
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(getJson("json/artist_1.json"))
            )

            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ROOT)

            val expectedResponse = RestArtistFull(
                createDate = dateFormat.parse("2023-01-01T00:00:00.000000"),
                description = "Test Artist Description 1",
                endDate = null,
                foreignId = 11,
                id = 1,
                imageUrl = "https://songschords/images/1.jpg",
                name = "Test Artist 1",
                nameAliases = "Test Artist Alias 1",
                resourceUri = "/api/v1/artist/1/",
                updateDate = dateFormat.parse("2023-01-01T01:00:00.000000")
            )

            val actualResponse = restService.getArtist(1).body()

            assertEquals(expectedResponse, actualResponse)
        }
    }

    @Test
    fun `should load artist with id 2`() {
        runBlocking {
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(getJson("json/artist_2.json"))
            )

            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ROOT)

            val expectedResponse = RestArtistFull(
                createDate = dateFormat.parse("2023-01-02T00:00:00.000000"),
                description = "Test Artist Description 2",
                endDate = null,
                foreignId = 12,
                id = 2,
                imageUrl = "https://songschords/images/2.jpg",
                name = "Test Artist 2",
                nameAliases = "Test Artist Alias 2",
                resourceUri = "/api/v1/artist/2/",
                updateDate = dateFormat.parse("2023-01-02T01:00:00.000000")
            )

            val actualResponse = restService.getArtist(2).body()

            assertEquals(expectedResponse, actualResponse)
        }
    }

    @Test
    fun `should load songs`() {
        runBlocking {
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(getJson("json/songs.json"))
            )

            val expectedResponse = RestResource(
                meta = RestResource.Meta(
                    limit = 20,
                    next = null,
                    offset = 0,
                    previous = null,
                    totalCount = 2
                ),
                objects = listOf(
                    RestSongShort(
                        id = 1,
                        name = "Song 1",
                        artist = 1,
                        artistName = "Artist 1",
                        resourceUri = "/api/v1/song/1/"
                    ),
                    RestSongShort(
                        id = 2,
                        name = "Song 2",
                        artist = 2,
                        artistName = "Artist 2",
                        resourceUri = "/api/v1/song/2/"
                    )
                )
            )

            val actualResponse = restService.getSongs().body()

            assertEquals(expectedResponse, actualResponse)
        }
    }

    @Test
    fun `should load song with id 1`() {
        runBlocking {
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(getJson("json/song_1.json"))
            )

            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ROOT)

            val expectedResponse = RestSongFull(
                artist = 1,
                artistName = "Artist 1",
                author = "Author 1",
                content = "Content 1",
                copyright = "Copyright 1",
                createDate = dateFormat.parse("2023-01-01T00:00:00.000000"),
                endDate = null,
                foreignId = 1,
                id = 1,
                name = "Song 1",
                resourceUri = "/api/v1/song/1/",
                updateDate = dateFormat.parse("2023-01-01T00:00:00.000000")
            )

            val actualResponse = restService.getSong(1).body()

            assertEquals(expectedResponse, actualResponse)
        }
    }

    @Test
    fun `should send hit to song with id 1`() {
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

            val expected = true
            val actual = restService.sendHit(RestSongHitRequest.build(1)).isSuccessful

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

            val expected = 500
            val actual = restService.sendHit(RestSongHitRequest.build(2)).code()

            assertEquals(expected, actual)
        }
    }

    @Test
    fun `should response 404`() {
        runBlocking {
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(404)
            )

            val expectedCode = 404
            val actualCode = restService.getSong(2).code()

            assertEquals(expectedCode, actualCode)
        }
    }
}