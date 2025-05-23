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
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.artsyactivity.ui.screens.art_info.ArtInfoScreen
import com.example.artsyactivity.ui.screens.art_info.ArtInfoViewModel
import com.example.artsyactivity.ui.screens.home.HomeScreen
import com.example.artsyactivity.ui.screens.home.MainViewModel
import com.example.artsyactivity.ui.screens.home.MainViewModel.UiAction
import com.example.artsyactivity.ui.screens.login.LoginScreen
import com.example.artsyactivity.ui.screens.login.LoginScreenViewModel
import com.example.artsyactivity.ui.screens.register.RegisterScreen
import com.example.artsyactivity.ui.screens.register.RegisterScreenViewModel
import com.example.artsyactivity.ui.screens.search.SearchScreen
import com.example.artsyactivity.ui.screens.search.SearchScreenViewModel
import com.example.artsyactivity.ui.theme.ArtsyActivityTheme
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

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
            SharedTransitionLayout  {
                val navController = rememberNavController()
                ArtsyActivityTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {

                        NavHost(
                            navController = navController,
                            startDestination = Destinations.HomeScreen
                        ) {
                            composable<Destinations.HomeScreen>(
                                enterTransition = { EnterTransition.None },
                            ) {
                                val uiState by mainViewModel.uiState.collectAsStateWithLifecycle()

                                val snackbarHostState = remember { SnackbarHostState() }
                                val lifecycleOwner = LocalLifecycleOwner.current

                                LaunchedEffect(Unit) {
                                    mainViewModel.uiEvent
                                        .flowWithLifecycle(
                                            lifecycle = lifecycleOwner.lifecycle,
                                            minActiveState = Lifecycle.State.STARTED
                                        )
                                        .onEach { event ->
                                            when (event) {
                                                is MainViewModel.UiEvent.ShowSnackbar -> {
                                                    snackbarHostState.showSnackbar(event.message)
                                                }
                                            }
                                        }
                                        .launchIn(this)
                                }

                                HomeScreen(
                                    uiState = uiState,
                                    uiAction = { action ->
                                        when (action) {
                                            UiAction.OnSearchClicked -> {
                                                navController.navigate(Destinations.SearchScreen)
                                            }

                                            is UiAction.OnArtistClicked -> {
                                                navController.navigate(
                                                    Destinations.ArtInfoScreen(
                                                        artistName = action.artistName,
                                                        artistId = action.artistId
                                                    )
                                                )
                                            }

                                            UiAction.OnLoginClicked -> {
                                                navController.navigate(Destinations.LoginScreen)
                                            }

                                            else -> mainViewModel.onUiAction(action)
                                        }
                                    },
                                    snackbarHostState = snackbarHostState,
                                    animatedContentScope = this
                                )
                            }

                            composable<Destinations.LoginScreen> {
                                val viewModel = viewModel<LoginScreenViewModel>()
                                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                                LoginScreen(
                                    uiState = uiState,
                                    uiAction = { action ->
                                        when (action) {
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
                                                        Log.d("LOGIN_RESPONSE", it.toString())
                                                        mainViewModel.updateUserInformation(it.user)
                                                        navController.popBackStack()
                                                    }
                                                )
                                            }
                                        }
                                    }
                                )
                            }

                            composable<Destinations.RegisterScreen> {
                                val viewModel = viewModel<RegisterScreenViewModel>()
                                RegisterScreen(
                                    viewModel = viewModel,
                                    onLoginClick = {
                                        navController.navigateUp()
                                    },
                                    onBackClick = {
                                        navController.navigateUp()
                                    },
                                    onRegisterSuccess = { user ->
                                        mainViewModel.updateUserInformation(user)
                                        navController.popBackStack(Destinations.HomeScreen, inclusive = false)
                                    }
                                )
                            }

                            composable<Destinations.SearchScreen> {
                                val viewModel = viewModel<SearchScreenViewModel>()
                                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                                val lifecycle = LocalLifecycleOwner.current
                                val mainViewModelUiState by mainViewModel.uiState.collectAsStateWithLifecycle()

                                LaunchedEffect(LocalLifecycleOwner.current) {

                                    viewModel.uiEvent.flowWithLifecycle(
                                        lifecycle = lifecycle.lifecycle,
                                        minActiveState = Lifecycle.State.STARTED
                                    ).onEach { event ->
                                        when (event) {
                                            ArtInfoViewModel.UiEvent.PopulateFavoriteArtistInformation -> {
                                                mainViewModelUiState.favoriteArtistId.onEach {
                                                    viewModel.updateFavoriteStatus(
                                                        isFavorite = true,
                                                        artistId = it
                                                    )
                                                }
                                            }

                                            is ArtInfoViewModel.UiEvent.UpdateFavoriteArtistInfoInMain -> {
                                                if (event.isFavorite) {
                                                    mainViewModel.updateFavoriteArtistId(event.artistId)
                                                } else {
                                                    mainViewModel.removeFavoriteArtistId(event.artistId)
                                                }
                                            }

                                            is ArtInfoViewModel.UiEvent.ShowSnackbar -> {
                                                Log.d("SnackbarEvent", event.message)
                                            }
                                        }
                                    }.launchIn(this)
                                }

                                SearchScreen(
                                    uiState = uiState,
                                    uiAction = viewModel::onUiAction,
                                    onArtistClick = { item ->
                                        navController.navigate(
                                            Destinations.ArtInfoScreen(
                                                artistName = item.title,
                                                artistId = item.artist_id
                                            )
                                        )
                                    },
                                    isLoggedIn = mainViewModel.uiState.value.isLoggedIn,
                                    onClose = { navController.popBackStack(Destinations.HomeScreen, inclusive = false) }
                                )
                            }


                            composable<Destinations.ArtInfoScreen> { backStackEntry ->
                                val viewModel = viewModel<ArtInfoViewModel>()
                                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                                val mainViewModelUiState by mainViewModel.uiState.collectAsStateWithLifecycle()

                                val lifecycle = LocalLifecycleOwner.current

                                LaunchedEffect(LocalLifecycleOwner.current) {

                                    viewModel.uiEvent.flowWithLifecycle(
                                        lifecycle = lifecycle.lifecycle,
                                        minActiveState = Lifecycle.State.STARTED
                                    ).onEach { event ->
                                        when (event) {
                                            ArtInfoViewModel.UiEvent.PopulateFavoriteArtistInformation -> {
                                                mainViewModelUiState.favoriteArtistId.onEach {
                                                    viewModel.updateFavoriteStatusForArtist(
                                                        isFavorite = true,
                                                        artistId = it
                                                    )
                                                }
                                            }

                                            is ArtInfoViewModel.UiEvent.UpdateFavoriteArtistInfoInMain -> {
                                                if (event.isFavorite) {
                                                    mainViewModel.updateFavoriteArtistId(event.artistId)
                                                } else {
                                                    mainViewModel.removeFavoriteArtistId(event.artistId)
                                                }
                                            }

                                            is ArtInfoViewModel.UiEvent.ShowSnackbar -> {
                                                Log.d("SnackbarEvent", event.message)
                                            }
                                        }
                                    }.launchIn(this)
                                }

                                ArtInfoScreen(
                                    uiState = uiState,
                                    uiEvent = viewModel.uiEvent,
                                    uiAction = { action ->
                                        viewModel.onUiAction(action)
                                    },
                                    onBackClick = {
                                        navController.navigateUp()
                                    },
                                    isLoggedIn = mainViewModelUiState.isLoggedIn
                                )
                            }
                        }
                    }
                }
            }

        }
    }
}