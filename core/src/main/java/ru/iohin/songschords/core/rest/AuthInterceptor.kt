package ru.iohin.songschords.core.rest

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import ru.iohin.songschords.core.BuildConfig

class AuthInterceptor: Interceptor {
    companion object {
        const val USERNAME = BuildConfig.REST_USERNAME
        const val API_KEY = BuildConfig.REST_API_KEY
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val authenticatedRequest = request.newBuilder()
            .header("Authorization", "ApiKey $USERNAME:$API_KEY")
            .build()
        return chain.proceed(authenticatedRequest)
    }
}