package com.example.skytrack.data

data class OpenSkyResponse(
    val time: Long, // Timestamp of the data
    val states: List<List<Any>>  // List of aircraft states
)