package com.example.skytrack.retro

import com.example.skytrack.data.FlightResponse
import com.example.skytrack.data.OpenSkyResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Base URL of the API
//private const val BASE_URL = "https://api.aviationstack.com/v1/"
//
//// Define API interface
//interface FlightApiService {
//    @GET("flights")
//    suspend fun getFlights(
//        @Query("access_key") apiKey: String,
//        @Query("limit") limit: Int = 5
//    ): FlightResponse
//}
//
//// Retrofit Instance
//object RetrofitInstance {
//    val api: FlightApiService by lazy {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(FlightApiService::class.java)
//    }
//}

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
