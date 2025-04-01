package com.example.skytrack.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.skytrack.viewmodel.LoginViewModel
import com.example.skytrack.views.screen.LoginScreen

fun NavGraphBuilder.loginNavGraph(
    navController: NavController,
    loginViewModel: LoginViewModel,
//    settingScreenViewModel: SettingScreenViewModel
) {
    navigation(
        startDestination = Screen.LoginScreen.route,
        route = "login_graph"
    ) {
        composable(Screen.LoginScreen.route) {
            LoginScreen(
                state = loginViewModel.signInResult,
                onSignInSuccess = {
                    navController.navigate("SetProfile") {
                        popUpTo("login_graph") { inclusive = true }
                    }
                },
                loginViewModel = loginViewModel
            )
        }

//        composable(Screen.SetProfile.route) {
//            SetProfile(
//                navController = navController,
//                settingScreenViewModel = settingScreenViewModel
//            )
//        }
    }
}