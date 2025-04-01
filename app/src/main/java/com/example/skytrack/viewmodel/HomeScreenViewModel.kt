package com.example.skytrack.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skytrack.api.apiKey
import com.example.skytrack.data.AircraftState
import com.example.skytrack.data.FlightData
import com.example.skytrack.data.NetworkUtils
import com.example.skytrack.data.NetworkUtils.isInternetAvailable
import com.example.skytrack.retro.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel : ViewModel() {
    private val _flights = MutableStateFlow<List<AircraftState>>(emptyList()) // Use StateFlow
    val flights: StateFlow<List<AircraftState>> = _flights // Expose as a StateFlow

    private val _showNoInternet = MutableStateFlow(false)
    val showNoInternet: StateFlow<Boolean> = _showNoInternet

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun checkInternetAvailability(context: Context) {
        // Check internet availability and update showNoInternet
        val isConnected = isInternetAvailable(context)
        _showNoInternet.value = !isConnected
    }

    fun fetchFlights() {
        viewModelScope.launch {
            try {
                _isLoading.value = true  // Start loading

                val response = RetrofitInstance.api.getAllAircraftStates()
                if (response != null) {
                    // Map the response to AircraftState objects and filter out domestic flights
                    val aircraftStates = response.states.mapNotNull { state ->
                        val aircraftState = AircraftState(
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

                        if (aircraftState.origin_country.trim() == "India" || aircraftState.callsign?.contains(
                                "IGO",
                                ignoreCase = true
                            ) == true
                        ) {
                            Log.d(
                                "FlightViewModel",
                                "Domestic Indigo Flight: ${aircraftState.callsign}"
                            )
                            aircraftState // Only add it to the list if it's a domestic flight from India (Indigo)
                        } else {
                            null // Ignore international flights
                        }
                    }

                    // Log the number of filtered flights
                    Log.d("FlightViewModel", "Filtered Domestic Flights: ${aircraftStates.size}")

                    _flights.value = aircraftStates // Update the flights list with filtered flights
                } else {
                    Log.e("FlightViewModel", "Error: Null response")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("FlightViewModel", "Exception: ${e.message}")
            } finally {
                _isLoading.value = false // End loading
            }
        }
    }
}
