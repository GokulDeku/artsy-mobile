package com.example.artsyactivity.ui.screens.art_info

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.artsyactivity.ArtsyApplication
import com.example.artsyactivity.Destinations
import com.example.artsyactivity.data.network.models.response.artistInfo.ArtistInfo
import com.example.artsyactivity.network.ApiResult
import com.example.artsyactivity.network.safeApiCall
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ArtInfoViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState())
    val uiState = _uiState.asStateFlow()

    val artistInfoService = ArtsyApplication.providesArtInfoService()

    val artistInfo = savedStateHandle.toRoute<Destinations.ArtInfoScreen>()

    init {
        _uiState.update {
            it.copy(
                artistTitle = artistInfo.artistName,
                artistId = artistInfo.artistId
            )
        }

        viewModelScope.launch {
            updateShouldShowLoader(true)
            val result = safeApiCall {
                artistInfoService.getArtistDetails(
                    id = artistInfo.artistId,
                    shouldShowSimilarArtist = false
                )
            }

            when (result) {
                is ApiResult.Success -> {
                    Log.d("VIJ", "Artist Info: ${result.data}")
                    updateShouldShowLoader(false)
                    updateArtistInfo(result.data)
                }

                is ApiResult.Error -> {
                    Log.d("VIJ", "Artist Info: ${result.error}")
                }
            }
        }
    }

    private fun updateShouldShowLoader(shouldShowLoader: Boolean) {
        _uiState.update {
            it.copy(
                shouldShowLoader = shouldShowLoader
            )
        }
    }

    private fun updateArtistInfo(artistInfo: ArtistInfo) {
        _uiState.update {
            it.copy(
                artistDetail = artistInfo
            )
        }
    }

    data class UiState(
        val artistTitle: String = "",
        val artistId: String = "",
        val shouldShowLoader: Boolean = true,
        val artistDetail: ArtistInfo? = null
    )
}