package com.example.artsyactivity.ui.screens.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.artsyactivity.ArtsyApplication
import com.example.artsyactivity.data.network.models.response.login.FavoriteArtist
import com.example.artsyactivity.data.network.models.response.shared.UserData
import com.example.artsyactivity.network.ApiResult
import com.example.artsyactivity.network.safeApiCall
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var shouldShowSplashScreen by mutableStateOf(true)

    private val authService = ArtsyApplication.providesAuthService()

    val snackbarMessage: String? = null

    init {
        _uiState.map {
            it.favoriteArtistId
        }.onEach {
            validateSessionCookie()
        }.launchIn(viewModelScope)
        validateSessionCookie()
    }

    private fun logout() {
        viewModelScope.launch {
            val result = safeApiCall {
                authService.logout()
            }

            when (result) {
                is ApiResult.Error -> {

                }

                is ApiResult.Success -> {
                    updateIsLoggedIn(false)
                    updateFavorites(emptyList())
                    updateUserImg("")
                    _uiEvent.send(UiEvent.ShowSnackbar("Logged out successfully"))
                }
            }
        }
    }

    private fun deleteAccount() {
        viewModelScope.launch {
            val result = safeApiCall {
                authService.deleteAccount()
            }

            when (result) {
                is ApiResult.Error -> {

                }

                is ApiResult.Success -> {
                    updateIsLoggedIn(false)
                    updateFavorites(emptyList())
                    updateUserImg("")
                    _uiEvent.send(UiEvent.ShowSnackbar("Account deleted successfully"))
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

            when (result) {
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

    fun updateUserInformation(user: UserData) {
        updateIsLoggedIn(true)
        updateFavorites(user.favoriteArtists.orEmpty())
        updateUserImg(user.userImg)
        viewModelScope.launch {
            _uiEvent.send(UiEvent.ShowSnackbar("Logged in successfully"))
        }
    }

    fun onUiAction(action: UiAction) {
        when (action) {
            is UiAction.OnArtistClicked -> {

            }

            is UiAction.OnLogOutClicked -> {
                logout()
            }

            is UiAction.OnDeleteAccountClicked -> {
                deleteAccount()
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
                favorites = favoriteArtists,
                favoriteArtistId = favoriteArtists.map { it.id }
            )
        }
    }

    fun updateFavoriteArtistId(artistId: String) {
        _uiState.update {
            it.copy(
                favoriteArtistId = it.favoriteArtistId.plus(artistId)
            )
        }
    }

    fun removeFavoriteArtistId(artistId: String) {
        _uiState.update {
            it.copy(
                favoriteArtistId = it.favoriteArtistId.plus(artistId)
            )
        }
    }


    sealed interface UiAction {
        data object OnLoginClicked : UiAction
        data class OnArtistClicked(val artistId: String, val artistName: String) : UiAction
        data object OnSearchClicked : UiAction
        data object OnLogOutClicked : UiAction
        data object OnDeleteAccountClicked : UiAction
    }

    sealed interface UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent
    }

    data class UiState(
        val isLoggedIn: Boolean = false,
        val userImg: String = "",
        val favorites: List<FavoriteArtist> = emptyList(),
        val favoriteArtistId: List<String> = listOf()
    )
}