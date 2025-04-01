package com.example.skytrack.views.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.skytrack.data.AircraftState
import com.example.skytrack.data.getLocationFromCoordinates

@Composable
fun FlightCard(
    flight: AircraftState,
    formatFlightCode: (String?) -> String
) {
    val context = LocalContext.current
    val formattedFlightCode = formatFlightCode(flight.callsign)

    // Mutable state to hold location
    var location by remember { mutableStateOf("Fetching location...") }

    // Fetch location asynchronously
    LaunchedEffect(flight.latitude, flight.longitude) {
        getLocationFromCoordinates(context, flight.latitude, flight.longitude) { result ->
            location = result
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Text(flight.callsign.toString())
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Flight Number: $formattedFlightCode", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Country: ${flight.origin_country}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Latitude: ${flight.latitude}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Longitude: ${flight.longitude}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Altitude: ${flight.baro_altitude} meters", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Location: $location", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Heading: ${flight.heading}°", style = MaterialTheme.typography.bodyMedium)
            Text(text = "On Ground: ${if (flight.on_ground) "Yes" else "No"}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
