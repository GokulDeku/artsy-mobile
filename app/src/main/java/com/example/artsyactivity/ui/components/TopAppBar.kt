@file:OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)

package com.example.artsyactivity.ui.components

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun SharedTransitionScope.TopBar(
    onSearchClick: () -> Unit,
    onUserClick: () -> Unit = {},
    animatedContentScope: AnimatedContentScope,
) {
    TopAppBar(
        title = { Text("Artist Search") },
        actions = {
            IconButton(
                modifier = Modifier
                    .sharedElement(
                        rememberSharedContentState(key = "search-icon-button"),
                        animatedVisibilityScope = animatedContentScope,
                    ),
                onClick = onSearchClick
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search"
                )
            }
            IconButton(onClick = onUserClick) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = "User"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}
