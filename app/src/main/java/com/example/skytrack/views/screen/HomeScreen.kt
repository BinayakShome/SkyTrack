package com.example.skytrack.views.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.skytrack.viewmodel.HomeScreenViewModel
import com.example.skytrack.views.components.FlightCard
import com.example.skytrack.views.components.NoInternet
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel
) {
    val context = LocalContext.current
    val showNoInternet by viewModel.showNoInternet.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val flights by viewModel.flights.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState() // Collect search query from ViewModel

    val firebaseUser = FirebaseAuth.getInstance().currentUser
    val userName = firebaseUser?.displayName
        ?.substringBefore('_')
        ?.substringBefore(' ')
        ?: "User"

    // Fetch data when screen is launched
    LaunchedEffect(Unit) {
        viewModel.checkInternetAvailability(context)
        viewModel.fetchFlights()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Hello, $userName",
                        color = Color(0xFF212121),
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F7FA))
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when {
                showNoInternet -> {
                    NoInternet()
                }
                isLoading -> {
                    CircularProgressIndicator(color = Color(0xFF1E88E5))
                }
                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        // Search bar for filtering by flight number or city
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { query -> viewModel.onSearchQueryChanged(query) },
                            label = { Text("Search Flight or City") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Display filtered flight list
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(flights) { flight ->
                                FlightCard(
                                    flight,
                                    formatFlightCode = { callsign -> viewModel.formatFlightCode(callsign) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

