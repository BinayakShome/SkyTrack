package com.example.skytrack.retro

import com.example.skytrack.data.OpenSkyResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface OpenSkyApiService {
    @GET("states/all")
    suspend fun getAllAircraftStates(): OpenSkyResponse
}

object RetrofitInstance {
    val api: OpenSkyApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://opensky-network.org/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenSkyApiService::class.java)
    }
}
