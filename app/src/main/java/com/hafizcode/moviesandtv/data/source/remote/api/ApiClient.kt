package com.hafizcode.moviesandtv.data.source.remote.api

import com.hafizcode.moviesandtv.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val httpClient = OkHttpClient.Builder().apply { addInterceptor(loggingInterceptor) }.build()

    private val retrofit: Retrofit.Builder by lazy {
        Retrofit.Builder().apply {
            client(httpClient)
            baseUrl(BuildConfig.TMBD_BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
        }
    }

    val instance: ApiService by lazy {
        retrofit.build().create(ApiService::class.java)
    }
}