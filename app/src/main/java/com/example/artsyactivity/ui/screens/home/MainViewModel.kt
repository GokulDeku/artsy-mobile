package com.example.artsyactivity.ui.screens.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.artsyactivity.ArtsyApplication
import com.example.artsyactivity.data.network.models.request.LoginData
import com.example.artsyactivity.data.network.models.response.FavoriteArtist
import com.example.artsyactivity.data.network.models.response.LoginResponse
import com.example.artsyactivity.network.ApiResult
import com.example.artsyactivity.network.safeApiCall
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    var shouldShowSplashScreen by mutableStateOf(true)

    private val authService = ArtsyApplication.providesAuthService()

    init {
        validateSessionCookie()
    }

    private fun logout() {
        viewModelScope.launch {
            val result = safeApiCall {
                authService.logout()
            }

            when(result) {
                is ApiResult.Error -> {

                }
                is ApiResult.Success -> {
                    updateIsLoggedIn(false)
                    updateFavorites(emptyList())
                    updateUserImg("")
                }
            }
        }
    }

    fun updateSplashScreenStatus(status: Boolean) {
        shouldShowSplashScreen = status
    }

    fun validateSessionCookie() {
        viewModelScope.launch {
            val result = safeApiCall {
                authService.autoLogin()
            }

            when(result) {
                is ApiResult.Error -> {
                    updateSplashScreenStatus(false)
                    Log.d("VIJ", "result: ${result.error}")
                }
                is ApiResult.Success -> {
                    updateSplashScreenStatus(false)
                    updateIsLoggedIn(result.data.isAuthorized)
                    updateFavorites(result.data.favoriteArtists)
                    updateUserImg(result.data.userImg)
                }
            }
        }
    }

    fun updateUserInformation(result: LoginResponse) {
        updateIsLoggedIn(true)
        updateFavorites(result.user.favoriteArtists.orEmpty())
        updateUserImg(result.user.userImg)
    }

    fun doLoginCall() {
        viewModelScope.launch {
            val result = safeApiCall {
                authService.login(
                    loginData = LoginData(
                        email = "vijay@gmail.com",
                        password = "vijaytest@1234"
                    )
                )
            }
        }
    }

    fun onUiAction(action: UiAction) {
        when (action) {
            is UiAction.OnArtistClicked -> {

            }

            is UiAction.OnLogOutClicked -> {
                logout()
            }

            else -> Unit
        }
    }

    fun updateIsLoggedIn(isLoggedIn: Boolean) {
        _uiState.update {
            it.copy(
                isLoggedIn = isLoggedIn
            )
        }
    }

    fun updateUserImg(userImg: String) {
        _uiState.update {
            it.copy(
                userImg = userImg
            )
        }
    }

    fun updateFavorites(favoriteArtists: List<FavoriteArtist>) {
        _uiState.update {
            it.copy(
                favorites = favoriteArtists
            )
        }
    }


    sealed interface UiAction {
        data object OnLoginClicked : UiAction
        data class OnArtistClicked(val artistId: String) : UiAction
        data object OnSearchClicked : UiAction
        data object OnLogOutClicked: UiAction
    }

    data class UiState(
        val isLoggedIn: Boolean = false,
        val userImg: String = "",
        val favorites: List<FavoriteArtist> = emptyList()
    )
}