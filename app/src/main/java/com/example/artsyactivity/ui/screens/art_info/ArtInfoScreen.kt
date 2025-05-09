@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.artsyactivity.ui.screens.art_info

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonSearch
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artsyactivity.ui.screens.art_info.components.ArtInfoTopBar
import com.example.artsyactivity.ui.screens.art_info.components.CategoryDialog
import kotlinx.coroutines.launch

@Composable
fun ArtInfoScreen(
    modifier: Modifier = Modifier,
    uiState: ArtInfoViewModel.UiState,
    uiAction: (ArtInfoViewModel.UiAction) -> Unit,
    onBackClick: () -> Unit,
    isLoggedIn: Boolean
) {
    val pagerState = rememberPagerState(
        pageCount = { 3 },
        initialPage = uiState.currentPage,
    )

    val coroutineScope = rememberCoroutineScope()

    val tabInfo = rememberSaveable {
        listOf<TabInfo>(
            TabInfo(
                title = "Details",
                icon = Icons.Outlined.Info,
                contentDescription = "Info Icon"
            ),
            TabInfo(
                title = "Artworks",
                icon = Icons.Outlined.AccountBox,
                contentDescription = "Artworks Icon"
            ),
            TabInfo(
                title = "Similar",
                icon = Icons.Filled.PersonSearch,
                contentDescription = "Similar Artist Icon"
            )
        )
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            ArtInfoTopBar(
                title = uiState.artistTitle,
                isFavorite = uiState.isArtistFavorite,
                isLoggedIn = isLoggedIn,
                onBackClick = onBackClick,
                onFavoriteClicked = {
                    uiAction(ArtInfoViewModel.UiAction.UpdateFavoriteArtist(uiState.artistId))
                }
            )
        }
    ) { innerPadding ->
        if (uiState.showCategoryDialog) {
            CategoryDialog(
                categories = uiState.selectedCategories,
                onDismiss = { uiAction(ArtInfoViewModel.UiAction.OnDismissCategoryDialog) }
            )
        }

        Column(modifier = Modifier.padding(innerPadding)) {
            SecondaryTabRow(
                selectedTabIndex = uiState.currentPage
            ) {
                tabInfo.forEachIndexed { index, tabInfo ->
                    Tab(
                        selected = uiState.currentPage == index,
                        onClick = {
                            uiAction(ArtInfoViewModel.UiAction.OnPageChanged(index))
                            coroutineScope.launch {
                                pagerState.scrollToPage(index)
                            }
                        },
                        text = {
                            Text(
                                text = tabInfo.title,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                color = TabRowDefaults.primaryContentColor
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = tabInfo.icon,
                                contentDescription = tabInfo.contentDescription,
                                tint = TabRowDefaults.primaryContentColor
                            )
                        }
                    )
                }
            }

            HorizontalPager(
                modifier = Modifier
                    .fillMaxSize(),
                state = pagerState,
                key = { it },
                userScrollEnabled = false
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (uiState.shouldShowLoader) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(50.dp)
                        )
                    } else {
                        if (uiState.currentPage == 0) {
                            DetailTab(
                                title = uiState.artistTitle,
                                birthInfo = uiState.artistDetail!!.getBirthInfo(),
                                biography = uiState.artistDetail.biography
                            )
                        } else if (uiState.currentPage == 1) {
                            ArtWorkTab(
                                artWorks = uiState.artWork!!.data,
                                onViewCategoryClicked = { artworkId ->
                                    uiAction(ArtInfoViewModel.UiAction.OnViewCategoryClicked(artworkId))
                                }
                            )
                        } else {
                            SimilarTab(
                                similarArtists = uiState.artistDetail!!.similarArtists,
                                onFavoriteIconClick = { artistId ->
                                    uiAction(ArtInfoViewModel.UiAction.UpdateFavoriteArtist(artistId))
                                },
                                onArtistCardClick = { artistId ->
                                    uiAction(
                                        ArtInfoViewModel.UiAction.OnSimilarArtistClicked(
                                            artistId
                                        )
                                    )
                                },
                                isLoggedIn = isLoggedIn
                            )
                        }
                    }
                }
            }
        }

    }
}


@Composable
fun DetailTab(
    modifier: Modifier = Modifier,
    title: String,
    birthInfo: String,
    biography: String
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )

        Text(
            modifier = Modifier.padding(top = 5.dp, bottom = 10.dp),
            text = birthInfo,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.W500
            )
        )

        Text(
            modifier = Modifier.padding(horizontal = 12.dp),
            style = MaterialTheme.typography.bodyMedium,
            text = biography
        )
    }
}

@Preview
@Composable
private fun PreviewArtInfoScreen() {
    ArtInfoScreen(
        uiState = ArtInfoViewModel.UiState(),
        uiAction = {

        },
        onBackClick = {

        },
        isLoggedIn = true
    )
}