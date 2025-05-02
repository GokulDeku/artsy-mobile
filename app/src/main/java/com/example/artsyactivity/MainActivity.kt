package com.example.artsyactivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.artsyactivity.ui.screens.home.HomeScreen
import com.example.artsyactivity.ui.screens.home.HomeScreenViewModel
import com.example.artsyactivity.ui.theme.ArtsyActivityTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            ArtsyActivityTheme {
                NavHost(
                    navController = navController,
                    startDestination = Destinations.HomeScreen
                ) {
                    composable<Destinations.HomeScreen>(
                        enterTransition = { EnterTransition.None },
                    ) {
                        HomeScreen(
                            uiState = HomeScreenViewModel.UiState(),
                            uiAction = {

                            }
                        )
                    }
                }
            }
        }
    }
}