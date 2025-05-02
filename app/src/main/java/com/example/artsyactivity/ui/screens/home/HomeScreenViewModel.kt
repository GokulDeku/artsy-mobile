package com.example.artsyactivity.ui.screens.home

import androidx.lifecycle.ViewModel
import com.example.artsyactivity.data.model.FavoriteArtist
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeScreenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onUiAction(action: UiAction) {
        when(action) {
            is UiAction.OnArtistClicked -> {

            }
            UiAction.OnLoginClicked -> {

            }

            else -> Unit
        }
    }


    sealed interface UiAction {
        data object OnLoginClicked : UiAction
        data class OnArtistClicked(val artistId: String): UiAction
        data object OnSearchClicked: UiAction
    }

    data class UiState(
        val isLoggedIn: Boolean = false,
        val favorites: List<FavoriteArtist> = emptyList()
    )
}