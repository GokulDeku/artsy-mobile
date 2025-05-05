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
import com.example.artsyactivity.ui.screens.search.components.ArtistDetail
import com.example.artsyactivity.ui.screens.search.components.SearchTopBar

@Composable
fun SharedTransitionScope.SearchScreen(
    modifier: Modifier = Modifier,
    uiState: SearchScreenViewModel.SearchScreenUiState,
    keyWordChange: (String) -> Unit,
    onCloseClick: () -> Unit,
    animatedContentScope: AnimatedContentScope
) {
    Scaffold(
        topBar = {
            SearchTopBar(
                animatedContentScope = animatedContentScope,
                onValueChange = keyWordChange,
                onCloseClick = onCloseClick
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
                    key(item.artist_id) {
                        ArtistDetail(
                            artistName = item.title,
                            imageUrl = item.img_src
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
private fun PreviewSearchScreen(modifier: Modifier = Modifier) {
    SharedTransitionLayout {
        AnimatedContent(true) {
            SearchScreen(
                uiState = SearchScreenViewModel.SearchScreenUiState(),
                animatedContentScope = this,
                onCloseClick = {

                },
                keyWordChange = {

                }
            )
        }
    }
}