package com.example.serializer

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
        @GET("Anelkad/serializer-db/db")
        suspend fun getOfferList(): Response<ApiResponse>
}

@Serializable
data class ApiResponse(val data: List<JsonObject>)