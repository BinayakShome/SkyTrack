package com.example.skytrack.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.skytrack.viewmodel.LoginViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    val loginViewModel: LoginViewModel = viewModel()
    val isUserLoggedIn by rememberUpdatedState(Firebase.auth.currentUser != null)
    val startDestination = if (isUserLoggedIn) "main" else "login_graph"

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        loginNavGraph(navController, loginViewModel = loginViewModel)
        mainNavGraph(navController)
    }
}