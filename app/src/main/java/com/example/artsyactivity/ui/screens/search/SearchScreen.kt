@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.example.artsyactivity.ui.screens.search

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.artsyactivity.data.network.models.response.search.ArtistDetail
import com.example.artsyactivity.ui.screens.search.components.ArtistDetail
import com.example.artsyactivity.ui.screens.search.components.SearchTopBar

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    uiState: SearchScreenViewModel.SearchScreenUiState,
    uiAction: (SearchScreenViewModel.UiAction) -> Unit,
    onArtistClick: (ArtistDetail) -> Unit = {},
    isLoggedIn: Boolean,
    onClose: () -> Unit
) {

    Scaffold(
        topBar = {
            SearchTopBar(
                keyword = uiState.keyword,
                uiAction = uiAction,
                onClose = onClose
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .padding(innerPadding)
                .padding(top = 20.dp)
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            uiState.searchResult?.let {
                itemsIndexed(uiState.searchResult.data) { index, item ->
                    key(item.artist_id, item.isFavorite) {
                        ArtistDetail(
                            artistName = item.title,
                            imageUrl = item.img_src,
                            shouldShowFavoriteIcon = isLoggedIn,
                            isFavorite = item.isFavorite,
                            onFavoriteIconClicked = {
                                uiAction(SearchScreenViewModel.UiAction.OnFavoriteClicked(item.artist_id))
                            },
                            onCardClick = {
                                onArtistClick(item)
                            }
                        )
                    }
                }
            }
        }
    }
}