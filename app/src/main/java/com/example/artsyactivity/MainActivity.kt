@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.example.artsyactivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.artsyactivity.ui.screens.art_info.ArtInfoScreen
import com.example.artsyactivity.ui.screens.home.HomeScreen
import com.example.artsyactivity.ui.screens.home.HomeScreenViewModel
import com.example.artsyactivity.ui.screens.search.SearchScreen
import com.example.artsyactivity.ui.theme.ArtsyActivityTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            ArtsyActivityTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SharedTransitionScope {
                        NavHost(
                            modifier = it,
                            navController = navController,
                            startDestination = Destinations.HomeScreen
                        ) {
                            composable<Destinations.HomeScreen>(
                                enterTransition = { EnterTransition.None },
                            ) {

                                val viewModel = viewModel<HomeScreenViewModel>()
                                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                                HomeScreen(
                                    uiState = uiState,
                                    uiAction = { action ->
                                        when (action) {
                                            HomeScreenViewModel.UiAction.OnSearchClicked -> {
                                                navController.navigate(Destinations.SearchScreen)
                                            }

                                            else -> viewModel.onUiAction(action)
                                        }
                                    },
                                    animatedContentScope = this
                                )
                            }

                            composable<Destinations.SearchScreen> {
                                SearchScreen(
                                    animatedContentScope = this
                                )
                            }


                            composable<Destinations.ArtInfoScreen> {
                                ArtInfoScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}