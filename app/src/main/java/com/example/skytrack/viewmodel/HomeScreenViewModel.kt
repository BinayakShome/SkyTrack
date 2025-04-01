package com.example.skytrack.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skytrack.data.AircraftState
import com.example.skytrack.data.NetworkUtils.isInternetAvailable
import com.example.skytrack.data.airlineCodes
import com.example.skytrack.retro.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel : ViewModel() {
    private val _flights = MutableStateFlow<List<AircraftState>>(emptyList()) // Original list of flights
    val flights: StateFlow<List<AircraftState>> = _flights // Exposed flights

    private val _showNoInternet = MutableStateFlow(false)
    val showNoInternet: StateFlow<Boolean> = _showNoInternet

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _searchQuery = MutableStateFlow("") // StateFlow for search query
    val searchQuery: StateFlow<String> = _searchQuery

    // Function to update the search query
    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        filterFlights(query)
    }

    // Function to filter flights based on the search query
    private fun filterFlights(query: String) {
        // Filter the list of flights based on the search query (callsign or city)
        _flights.value = _flights.value.filter { flight ->
            val queryLowerCase = query.lowercase()

            // Check if the query matches the flight number (callsign) or city (origin_country)
            flight.callsign?.lowercase()?.contains(queryLowerCase) == true ||
                    flight.origin_country.lowercase().contains(queryLowerCase)
        }
    }

    // Other functions like checking internet availability and fetching flights
    fun checkInternetAvailability(context: Context) {
        val isConnected = isInternetAvailable(context)
        _showNoInternet.value = !isConnected
    }

    fun fetchFlights() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getAllAircraftStates()
                if (response != null) {
                    val aircraftStates = response.states.mapNotNull { state ->
                        AircraftState(
                            icao24 = state[0] as String,
                            callsign = state[1] as? String,
                            origin_country = state[2] as String,
                            time_position = state[3] as? Double,
                            last_contact = state[4] as? Double,
                            latitude = state[5] as? Double,
                            longitude = state[6] as? Double,
                            baro_altitude = state[7] as? Double,
                            on_ground = state[8] as Boolean,
                            velocity = state[9] as? Double,
                            heading = state[10] as? Double,
                            vertical_rate = state[11] as? Double
                        )
                    }

                    // Filter out non-Indian flights
                    val domesticFlights = aircraftStates.filter {
                        it.origin_country.trim() == "India"
                    }
                    _flights.value = domesticFlights // Set the filtered list
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun formatFlightCode(callsign: String?): String {
        return if (!callsign.isNullOrEmpty() && callsign.length > 2) {
            val iataCode = callsign.take(3).uppercase()  // Take first two chars as IATA code
            val flightNumber = callsign.drop(3)  // Take the rest as flight number

            val airlineName =
                airlineCodes[iataCode] ?: "Unknown Airline" // Get airline name from map

            "$airlineName $flightNumber"  // Format as "Air India 1382"
        } else {
            callsign ?: "Unknown"  // Return original callsign if it's invalid or empty
        }
    }
}