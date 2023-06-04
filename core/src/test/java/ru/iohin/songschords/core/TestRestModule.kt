package ru.iohin.songschords.core

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import ru.iohin.songschords.core.rest.RestService
import javax.inject.Singleton

@Module
class TestRestModule {
    @Provides
    @Singleton
    fun providesMockWebServer() = MockWebServer().apply {
        dispatcher = MockResponseDispatcher()
    }

    @Provides
    fun providesRestService(server: MockWebServer) = Retrofit.Builder()
        .baseUrl(server.url(RestService.API_PATH))
        .addConverterFactory(
            Json.asConverterFactory("application/json".toMediaTypeOrNull()!!)
        )
        .build()
        .create(RestService::class.java)
}