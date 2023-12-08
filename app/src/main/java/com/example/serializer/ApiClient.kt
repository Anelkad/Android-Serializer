package com.example.serializer

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit


object ApiClient {

    private const val BASE_URL = "https://my-json-server.typicode.com/"

    private val okHttpClientClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
        )
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClientClient)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()

    val service = retrofit.create(ApiService::class.java)
}