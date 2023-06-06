package ru.iohin.songschords.core.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import ru.iohin.songschords.core.BuildConfig
import ru.iohin.songschords.core.rest.AuthInterceptor
import ru.iohin.songschords.core.rest.RestService
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class RestModule {
    private fun createClient() = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(AuthInterceptor())
        .build()

    @Provides
    @Singleton
    fun providesRestService() = Retrofit.Builder()
        .client(createClient())
        .baseUrl("${BuildConfig.REST_URL}${RestService.API_PATH}")
        .addConverterFactory(
            Json.asConverterFactory(MediaType.parse("application/json")!!)
        )
        .build()
        .create(RestService::class.java)
}