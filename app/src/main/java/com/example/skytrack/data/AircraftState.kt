package com.example.skytrack.data

data class AircraftState(
    val icao24: String,  // ICAO24 identifier for the aircraft
    val callsign: String?, // Callsign (flight number or identifier)
    val origin_country: String,  // Country of origin
    val time_position: Double?, // Time of position data (Unix timestamp as Double?)
    val last_contact: Double?,  // Last contact time (Unix timestamp as Double?)
    val latitude: Double?,   // Latitude of the aircraft
    val longitude: Double?,  // Longitude of the aircraft
    val baro_altitude: Double?, // Barometric altitude of the aircraft
    val on_ground: Boolean,   // Whether the aircraft is on the ground or not
    val velocity: Double?,  // Velocity of the aircraft (in m/s)
    val heading: Double?,  // Heading of the aircraft (in degrees)
    val vertical_rate: Double?  // Vertical rate (rate of climb/descent in m/s)
)