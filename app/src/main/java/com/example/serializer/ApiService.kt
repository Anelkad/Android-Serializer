package com.example.serializer

import PolymorphicItem
import com.google.gson.JsonObject
import kotlinx.serialization.json.JsonElement
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
        @GET("Anelkad/serializer-db/db")
        suspend fun getOfferList(): Response<ApiResponse>
}

data class ApiResponse(val data: List<JsonObject>)