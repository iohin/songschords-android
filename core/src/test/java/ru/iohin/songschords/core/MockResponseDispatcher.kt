package ru.iohin.songschords.core

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import ru.iohin.songschords.core.rest.RestService
import java.io.File

class MockResponseDispatcher: Dispatcher() {
    private fun getJson(path : String) : String {
        val uri = this.javaClass.classLoader.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }

    override fun dispatch(request: RecordedRequest): MockResponse {
        return when (request.path) {
            "${RestService.API_PATH}artist/?limit=20" ->
                MockResponse()
                    .setResponseCode(200)
                    .setBody(getJson("json/artists.json"))
            "${RestService.API_PATH}artist/1/" ->
                MockResponse()
                    .setResponseCode(200)
                    .setBody(getJson("json/artist_1.json"))
            "${RestService.API_PATH}artist/2/" ->
                MockResponse()
                    .setResponseCode(200)
                    .setBody(getJson("json/artist_2.json"))
            "${RestService.API_PATH}song/?limit=20" ->
                MockResponse()
                    .setResponseCode(200)
                    .setBody(getJson("json/songs.json"))
            "${RestService.API_PATH}song/1/" ->
                MockResponse()
                    .setResponseCode(200)
                    .setBody(getJson("json/song_1.json"))
            "${RestService.API_PATH}song_hit/" -> {
                val expectedBody = """{"song":"/api/v1/song/1/"}"""
                if (request.body.readUtf8() == expectedBody) {
                    MockResponse()
                        .setResponseCode(200)
                } else {
                    MockResponse()
                        .setResponseCode(500)
                }
            }
            else -> MockResponse().setResponseCode(404)
        }
    }
}