package com.example.artsyactivity.ui.screens.art_info

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.artsyactivity.ArtsyApplication
import com.example.artsyactivity.Destinations
import com.example.artsyactivity.data.network.models.response.artistInfo.ArtWork
import com.example.artsyactivity.data.network.models.response.artistInfo.ArtistInfo
import com.example.artsyactivity.data.network.models.response.login.FavoriteArtist
import com.example.artsyactivity.network.ApiResult
import com.example.artsyactivity.network.safeApiCall
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ArtInfoViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

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
                    shouldShowSimilarArtist = true
                )
            }

            when (result) {
                is ApiResult.Success -> {
                    Log.d("VIJ", "Artist Info: ${result.data}")
                    updateShouldShowLoader(false)
                    updateArtistInfo(result.data)
                    viewModelScope.launch {
                        _uiEvent.send(UiEvent.PopulateFavoriteArtistInformation)
                    }
                }

                is ApiResult.Error -> {
                    Log.d("VIJ", "Artist Info: ${result.error}")
                }
            }
        }
    }

    fun updateArtWork(artWork: ArtWork) {
        _uiState.update {
            it.copy(
                artWork = artWork
            )
        }
    }

    private fun updateFavoriteArtist(artistId: String) {
        viewModelScope.launch {
            val result = safeApiCall {
                artistInfoService.updateFavoriteArtist(artistId)
            }

            when (result) {
                is ApiResult.Error -> {
                    Log.d("VIJ", "Error updating favorites")
                }

                is ApiResult.Success -> {
                    updateFavoriteStatusForArtist(result.data.isFavorite, artistId)
                    _uiEvent.send(
                        UiEvent.UpdateFavoriteArtistInfoInMain(
                            artistId, result.data.isFavorite
                        )
                    )
                }
            }
        }
    }

    fun updateFavoriteStatusForArtist(isFavorite: Boolean, artistId: String) {
        if(artistId == uiState.value.artistId) {
            _uiState.update {
                it.copy(
                    isArtistFavorite = isFavorite
                )
            }
        }
        _uiState.update {
            it.copy(
                artistDetail = it.artistDetail?.copy(
                    similarArtists = it.artistDetail.similarArtists.map {
                        if (it.artist_id == artistId)
                            it.copy(isFavorite = isFavorite)
                        else
                            it
                    }
                )
            )
        }
    }

    private fun getArtWorkDetails(artistId: String) {
        viewModelScope.launch {
            updateShouldShowLoader(true)
            val result = safeApiCall {
                artistInfoService.getArtWorkCategories(
                    artWorkId = artistId
                )
            }

            when (result) {
                is ApiResult.Error -> {
                    Log.d("VIJ", "error fetching artwork details: ${result.error}")
                }

                is ApiResult.Success -> {
                    updateArtWork(result.data)
                    updateShouldShowLoader(false)
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

    fun onUiAction(action: UiAction) {
        when (action) {
            is UiAction.OnPageChanged -> {

                updateCurrentPage(action.page)

                if (action.page == 1 && uiState.value.artWork == null) {
                    getArtWorkDetails(artistInfo.artistId)
                }
            }

            is UiAction.UpdateFavoriteArtist -> {
                updateFavoriteArtist(action.artistId)
            }
        }
    }

    private fun updateCurrentPage(page: Int) {
        _uiState.update {
            it.copy(
                currentPage = page
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

    sealed interface UiAction {
        data class OnPageChanged(val page: Int) : UiAction
        data class UpdateFavoriteArtist(val artistId: String) : UiAction
    }

    sealed interface UiEvent {
        data object PopulateFavoriteArtistInformation : UiEvent
        data class UpdateFavoriteArtistInfoInMain(val artistId: String, val isFavorite: Boolean) :
            UiEvent
    }

    data class UiState(
        val artistTitle: String = "",
        val currentPage: Int = 0,
        val artistId: String = "",
        val isArtistFavorite: Boolean = false,
        val shouldShowLoader: Boolean = true,
        val artistDetail: ArtistInfo? = null,
        val artWork: ArtWork? = null,
        val favoriteArtists: List<FavoriteArtist> = emptyList()
    )
}