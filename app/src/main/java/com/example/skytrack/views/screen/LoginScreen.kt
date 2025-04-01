package com.example.skytrack.views.screen

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.skytrack.data.SignInResult
import com.example.skytrack.viewmodel.LoginViewModel
import com.example.skytrack.views.components.LoginButton

@Composable
fun LoginScreen(
    state: SignInResult?,
    onSignInSuccess: () -> Unit,
    loginViewModel: LoginViewModel = viewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(context) {
        loginViewModel.initGoogleSignIn(context as Activity)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        loginViewModel.handleSignInResult(result.data)
    }

    LaunchedEffect(state?.success) {
        if (state?.success != null) {
            onSignInSuccess()
        }
    }

    val isVisible = remember { mutableStateOf(false) } // Ensure animation state is preserved

    LaunchedEffect(Unit) {
        isVisible.value = true // Trigger animation when screen loads
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "SkyTrack",
            color = Color(0xFF1E88E5),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium,
            fontSize = 56.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Tagline with slower right slide-in animation
        AnimatedVisibility(
            visible = isVisible.value, // Make it visible after launch
            enter = slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(
                    durationMillis = 1000, // Slower animation (1 second)
                    easing = FastOutSlowInEasing // Smooth easing
                )
            ) + fadeIn(animationSpec = tween(durationMillis = 1000)),
            exit = fadeOut(animationSpec = tween(durationMillis = 500))
        ) {
            Text(
                text = "Your Flight, Your Time, Real-Time!",
                color = Color.Gray,
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        LoginButton(
            iconOnly = false,
            onClick = {
                loginViewModel.startGoogleSignIn(context, launcher)
            }
        )
    }
}
