package com.example.skytrack.data

data class FlightData(
    val flight_number: String?,
    val airline: Airline?,
    val departure: AirportInfo?,
    val arrival: AirportInfo?
)