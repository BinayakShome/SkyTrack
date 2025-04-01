package com.example.skytrack.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation

fun NavGraphBuilder.mainNavGraph(
    navController: NavController
) {
    navigation(
        startDestination = Screen.HomeScreen.route,
        route = "main"
    ) {
//        composable(Screen.CurrentDayScreen.route) {
//            val currentDayViewModel: CurrentDayViewModel = viewModel()
//
//            CurrentDayScreen(
//                navController = navController,
//                viewModel = currentDayViewModel
//            )
//        }
//
//        composable(Screen.ProfileScreen.route) {
//            val profileViewModel: ProfileViewModel = viewModel()
//
//            ProfileScreen(
//                navController = navController,
//                onLogOutClick = {
//                    Firebase.auth.signOut()
//                    navController.navigate("login_graph") {
//                        popUpTo("main") { inclusive = true }
//                    }
//                },
//                viewModel = profileViewModel
//            )
//        }
//
//        composable(Screen.WeekScreen.route) {
//            val weekViewModel : WeekViewModel = viewModel()
//
//            WeekScreen(navController = navController,
//                viewModel = weekViewModel)
//        }
//
//        composable(Screen.HolidayListScreen.route) {
//            HolidayListScreen(navController = navController)
//        }
//
//        composable(Screen.SettingScreen.route) {
//            val settingScreenViewModel: SettingScreenViewModel = viewModel()
//
//            SettingScreen(settingScreenViewModel = settingScreenViewModel,
//                navController = navController)
//        }
    }
}