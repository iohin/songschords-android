package ru.iohin.songschords.core.data.rest

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface SongsChordsService {
    @GET("artist/")
    suspend fun getArtists(
        @Query("name__icontains") searchName: String? = null
    ): Response<RestResource<RestArtistShort>>

    @GET("artist/{id}/")
    suspend fun getArtist(@Path("id") id: Int): Response<RestArtistFull>

    @GET("song/")
    suspend fun getSongs(
        @Query("artist_id") artistId: Int? = null,
        @Query("name__icontains") searchName: String? = null,
        @Query("content__icontains") searchContent: String? = null
    ): Response<RestResource<RestSongShort>>

    @GET("song/{id}/")
    suspend fun getSong(@Path("id") id: Int): Response<RestSongFull>

    @POST("song_hit/")
    suspend fun sendHit(@Body songHitRequest: RestSongHitRequest): Response<ResponseBody>

    companion object {
        const val API_PATH = "/api/v1/"
    }
}