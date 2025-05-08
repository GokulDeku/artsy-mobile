@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.example.artsyactivity.ui.screens.search

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.artsyactivity.data.network.models.response.search.ArtistDetail
import com.example.artsyactivity.ui.screens.search.components.ArtistDetail
import com.example.artsyactivity.ui.screens.search.components.SearchTopBar

@Composable
fun SharedTransitionScope.SearchScreen(
    modifier: Modifier = Modifier,
    uiState: SearchScreenViewModel.SearchScreenUiState,
    uiAction: (SearchScreenViewModel.UiAction) -> Unit,
    animatedContentScope: AnimatedContentScope,
    onArtistClick: (ArtistDetail) -> Unit = {},
    isLoggedIn: Boolean
) {
    Scaffold(
        topBar = {
            SearchTopBar(
                animatedContentScope = animatedContentScope,
                keyword = uiState.keyword,
                uiAction = uiAction,
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


@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Preview
@Composable
private fun PreviewSearchScreen() {
    SharedTransitionLayout {
        AnimatedContent(true) {
            SearchScreen(
                uiState = SearchScreenViewModel.SearchScreenUiState(),
                uiAction = {},
                animatedContentScope = this,
                isLoggedIn = false
            )
        }
    }
}