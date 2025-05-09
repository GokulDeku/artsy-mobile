@file:OptIn(FlowPreview::class)

package com.example.artsyactivity.ui.screens.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.artsyactivity.ArtsyApplication
import com.example.artsyactivity.data.network.models.response.search.SearchResult
import com.example.artsyactivity.network.ApiResult
import com.example.artsyactivity.network.safeApiCall
import com.example.artsyactivity.ui.screens.art_info.ArtInfoViewModel.UiEvent
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchScreenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SearchScreenUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val searchService = ArtsyApplication.providesSearchService()
    val artistInfoService = ArtsyApplication.providesArtInfoService()

    init {

        viewModelScope.launch {
            _uiState
                .map { it.keyword }
                .filter { it.isNotBlank() && it.length > 3 }
                .debounce(300)
                .distinctUntilChanged()
                .collectLatest { query ->
                    val result = safeApiCall {
                        searchService.searchArtists(query)
                    }
                    when (result) {
                        is ApiResult.Error -> {
                            Log.d("VIJ", result.error.toString())
                        }

                        is ApiResult.Success -> {
                            Log.d("VIJ", result.data.toString())
                            updateSearchResult(result.data)
                        }
                    }
                }
        }

    }

    fun updateSearchResult(searchResult: SearchResult) {
        _uiState.update {
            it.copy(
                searchResult = searchResult
            )
        }
        viewModelScope.launch {
            _uiEvent.send(UiEvent.PopulateFavoriteArtistInformation)
        }
    }

    fun updateFavoriteStatus(isFavorite: Boolean, artistId: String) {
        _uiState.update {
            it.copy(
                searchResult = it.searchResult?.copy(
                    data = it.searchResult.data.map {
                        if (it.artist_id == artistId) {
                            it.copy(isFavorite = isFavorite)
                        } else {
                            it
                        }
                    }
                )
            )
        }
    }

    fun clearKeyword() {
        _uiState.update {
            it.copy(
                keyword = ""
            )
        }
    }

    fun onKeywordChange(keyword: String) {
        _uiState.update {
            it.copy(
                keyword = keyword
            )
        }
    }

    fun onUiAction(action: UiAction) {
        when (action) {
            UiAction.ClearKeyWord -> {
                clearKeyword()
            }

            is UiAction.OnKeyWordChange -> {
                onKeywordChange(action.keyword)
            }

            is UiAction.OnFavoriteClicked -> {
                updateFavoriteArtist(action.artistId)
            }
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
                    updateFavoriteStatus(result.data.isFavorite, artistId)
                    _uiEvent.send(
                        UiEvent.UpdateFavoriteArtistInfoInMain(
                            artistId, result.data.isFavorite
                        )
                    )
                }
            }
        }
    }

    sealed interface UiAction {
        data object ClearKeyWord : UiAction
        data class OnKeyWordChange(val keyword: String) : UiAction
        data class OnFavoriteClicked(val artistId: String) : UiAction
    }


    data class SearchScreenUiState(
        val keyword: String = "",
        val searchResult: SearchResult? = null
    )
}