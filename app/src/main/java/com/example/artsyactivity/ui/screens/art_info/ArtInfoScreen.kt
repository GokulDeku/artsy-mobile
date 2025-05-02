@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.artsyactivity.ui.screens.art_info

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonSearch
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.artsyactivity.ui.screens.art_info.components.ArtInfoTopBar
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun ArtInfoScreen(modifier: Modifier = Modifier) {
    var state by rememberSaveable { mutableIntStateOf(0) }
    val pagerState = rememberPagerState(
        pageCount = { 3 },
        initialPage = 0,
        initialPageOffsetFraction = 0.5f
    )

    LaunchedEffect(Unit) {
        snapshotFlow {
            pagerState.currentPage
        }.distinctUntilChanged().collect { page ->
            state = page
        }
    }

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
                onBackClick = {},
                onFavoriteClicked = {}
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            SecondaryTabRow(
                selectedTabIndex = state
            ) {
                tabInfo.forEachIndexed { index, tabInfo ->
                    Tab(
                        selected = state == index,
                        onClick = { state = index },
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
                modifier = Modifier.fillMaxSize(),
                state = pagerState,
                verticalAlignment = Alignment.Top
            ) {

            }
        }

    }
}

@Preview
@Composable
private fun PreviewArtInfoScreen() {
    ArtInfoScreen()
}