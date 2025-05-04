package com.example.artsyactivity.network

import android.content.Context
import com.example.artsyactivity.service.AuthService
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit


object NetworkModule {

    const val BASE_URL = "https://squirtle-420690.wl.r.appspot.com/api/"
    private var retrofit: Retrofit? = null

    private fun getCookieJar(context: Context): PersistentCookieJar {
        return PersistentCookieJar(
            SetCookieCache(),
            SharedPrefsCookiePersistor(context)
        )
    }

    private fun getOkHttpClient(context: Context): OkHttpClient {
        val okHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .cookieJar(getCookieJar(context))
            .build()
        return okHttpClient
    }

    private fun getRetrofit(context: Context): Retrofit {
        val networkJson = Json { ignoreUnknownKeys = true }
        val contentType = "application/json".toMediaType()
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getOkHttpClient(context))
                .addConverterFactory(
                    networkJson.asConverterFactory(contentType)
                )
                .build()
        }
        return retrofit!!
    }

    fun provideAuthService(context: Context): AuthService {
        return getRetrofit(context).create(AuthService::class.java)
    }
}