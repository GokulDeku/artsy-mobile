@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.artsyactivity.ui.screens.art_info.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ArtInfoTopBar(
    modifier: Modifier = Modifier,
    isFavorite: Boolean = false,
    title: String,
    onBackClick: () -> Unit,
    onFavoriteClicked: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Art Info Back Icon"
                )
            }
        },
        actions = {
            IconButton(onClick = onFavoriteClicked) {
                Icon(
                    imageVector = if(isFavorite) Icons.Filled.Star else Icons.Rounded.StarOutline,
                    contentDescription = "Favorite Icon"
                )
            }
        },
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}

@Composable
@Preview
fun PreviewArtInfoTopBar() {
    ArtInfoTopBar(
        title = "Pablo Picasso",
        onBackClick = {},
        onFavoriteClicked = {}
    )
}