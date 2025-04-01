package com.example.skytrack.data

import android.content.Context
import android.location.Geocoder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

fun getLocationFromCoordinates(context: Context, latitude: Double?, longitude: Double?, onResult: (String) -> Unit) {
    if (latitude == null || longitude == null || latitude !in -90.0..90.0 || longitude !in -180.0..180.0) {
        onResult("Invalid location")
        return
    }

    val geocoder = Geocoder(context, Locale.getDefault())
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            val locationName = if (!addresses.isNullOrEmpty()) {
                addresses[0].getAddressLine(0) ?: "Unknown location"
            } else {
                "Unknown location"
            }

            withContext(Dispatchers.Main) {
                onResult(locationName)  // Update UI with the result
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                onResult("Location unavailable")
            }
        }
    }
}
