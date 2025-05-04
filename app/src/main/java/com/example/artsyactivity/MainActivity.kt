@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.example.artsyactivity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.artsyactivity.ui.screens.art_info.ArtInfoScreen
import com.example.artsyactivity.ui.screens.home.HomeScreen
import com.example.artsyactivity.ui.screens.home.MainViewModel
import com.example.artsyactivity.ui.screens.home.MainViewModel.UiAction
import com.example.artsyactivity.ui.screens.login.LoginScreen
import com.example.artsyactivity.ui.screens.login.LoginScreenViewModel
import com.example.artsyactivity.ui.screens.register.RegisterScreen
import com.example.artsyactivity.ui.screens.search.SearchScreen
import com.example.artsyactivity.ui.theme.ArtsyActivityTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val mainViewModel by viewModels<MainViewModel>()

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                mainViewModel.shouldShowSplashScreen
            }
        }

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
                                val uiState by mainViewModel.uiState.collectAsStateWithLifecycle()

                                HomeScreen(
                                    uiState = uiState,
                                    uiAction = { action ->
                                        when (action) {
                                            UiAction.OnSearchClicked -> {
                                                navController.navigate(Destinations.SearchScreen)
                                            }

                                            UiAction.OnLoginClicked -> {
                                                navController.navigate(Destinations.LoginScreen)
                                            }

                                            else -> mainViewModel.onUiAction(action)
                                        }
                                    },
                                    animatedContentScope = this
                                )
                            }

                            composable<Destinations.LoginScreen> {
                                val viewModel = viewModel<LoginScreenViewModel>()
                                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                                LoginScreen(
                                    uiState = uiState,
                                    uiAction = { action ->
                                        when(action) {
                                            LoginScreenViewModel.UiAction.OnBackClicked -> {
                                                navController.navigateUp()
                                            }

                                            LoginScreenViewModel.UiAction.OnRegisterClicked -> {
                                                navController.navigate(Destinations.RegisterScreen)
                                            }

                                            is LoginScreenViewModel.UiAction.OnLoginClicked -> {
                                                viewModel.onLoginClick(
                                                    email = action.email,
                                                    password = action.password,
                                                    onLoginSuccess = {
                                                        Log.d("VIJ", "onLoginSuccess: $it")
                                                        mainViewModel.updateUserInformation(it)
                                                        navController.popBackStack()
                                                    }
                                                )
                                            }
                                        }
                                    }
                                )
                            }

                            composable<Destinations.RegisterScreen> {
                                RegisterScreen(
                                    onLoginClick = {
                                        navController.navigateUp()
                                    },
                                    onBackClick = {
                                        navController.navigateUp()
                                    }
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