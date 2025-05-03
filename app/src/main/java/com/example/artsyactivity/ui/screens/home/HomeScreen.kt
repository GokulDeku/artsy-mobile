@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.example.artsyactivity.ui.screens.home

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.artsyactivity.data.model.FavoriteArtist
import com.example.artsyactivity.ui.components.FavoriteArtistListItem
import com.example.artsyactivity.ui.components.TopBar
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun SharedTransitionScope.HomeScreen(
    animatedContentScope: AnimatedContentScope,
    uiState: HomeScreenViewModel.UiState,
    uiAction: (HomeScreenViewModel.UiAction) -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopBar(
                isLoggedIn = uiState.isLoggedIn,
                animatedContentScope = animatedContentScope,
                onSearchClick = {
                    uiAction(
                        HomeScreenViewModel.UiAction.OnSearchClicked
                    )
                },
                onUserClick = {

                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            val currentDate = remember {
                val calender = Calendar.getInstance()
                val dateFormat = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
                dateFormat.format(calender.time)
            }

            Text(
                text = currentDate,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .background(MaterialTheme.colorScheme.surfaceContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Favorites",
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            when {
                !uiState.isLoggedIn -> {
                    Button(
                        onClick = {
                            uiAction(HomeScreenViewModel.UiAction.OnLoginClicked)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 100.dp, vertical = 10.dp),
                    ) {
                        Text("Log in to see favorites")
                    }
                }

                uiState.favorites.isEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No favorites")
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                    ) {
                        items(uiState.favorites.size) { index ->

                            key(uiState.favorites[index].id) {
                                FavoriteArtistListItem(
                                    artist = uiState.favorites[index],
                                    onClick = {
                                        uiAction(
                                            HomeScreenViewModel.UiAction.OnArtistClicked(
                                                uiState.favorites[index].id
                                            )
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Powered by Artsy",
                    style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
                    modifier = Modifier.clickable {
                        val intent = Intent(Intent.ACTION_VIEW, "https://www.artsy.net/".toUri())
                        context.startActivity(intent)
                    }
                )
            }
        }
    }
}

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Preview
@Composable
fun HomeScreenPreview_LoggedOut() {
    SharedTransitionLayout {
        AnimatedContent(true) {
            HomeScreen(
                uiState = HomeScreenViewModel.UiState(
                    isLoggedIn = false
                ),
                uiAction = {

                },
                animatedContentScope = this
            )
        }
    }
}


@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Preview
@Composable
fun HomeScreenPreview_LoggedInNoFavorites() {
    SharedTransitionLayout {
        AnimatedContent(true) {
            HomeScreen(
                uiState = HomeScreenViewModel.UiState(
                    isLoggedIn = true
                ),
                uiAction = {

                },
                animatedContentScope = this
            )
        }
    }
}


@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Preview
@Composable
fun HomeScreenPreview_LoggedInFavorites() {
    SharedTransitionLayout {
        AnimatedContent(true) {
            HomeScreen(
                uiState = HomeScreenViewModel.UiState(
                    isLoggedIn = true,
                    favorites = listOf(
                        FavoriteArtist(
                            id = "1",
                            name = "Calude Monet",
                            nationality = "French",
                            birthdate = "01/01/1840",
                            addedTime = "4 seconds ago"
                        ),
                        FavoriteArtist(
                            id = "2",
                            name = "Pablo Picasso",
                            nationality = "Spanish",
                            birthdate = "01/01/1881",
                            addedTime = "15 seconds ago"
                        )
                    )
                ),
                uiAction = {

                },
                animatedContentScope = this
            )
        }
    }
}