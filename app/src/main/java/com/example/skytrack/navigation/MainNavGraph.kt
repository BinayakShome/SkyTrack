package com.example.skytrack.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.skytrack.viewmodel.HomeScreenViewModel
import com.example.skytrack.views.screen.HomeScreen

fun NavGraphBuilder.mainNavGraph(
    navController: NavController
) {
    navigation(
        startDestination = Screen.HomeScreen.route,
        route = "main"
    ) {

        composable(Screen.HomeScreen.route){
            val homeScreenViewModel: HomeScreenViewModel = viewModel()

            HomeScreen(navController = navController,
                viewModel = homeScreenViewModel)
        }
    }
}