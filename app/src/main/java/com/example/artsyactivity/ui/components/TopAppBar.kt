@file:OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)

package com.example.artsyactivity.ui.components

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage


@Composable
fun SharedTransitionScope.TopBar(
    isLoggedIn: Boolean,
    userImg: String,
    onSearchClick: () -> Unit,
    onLogOutClick: () -> Unit,
    onDeleteAccountClick: () -> Unit,
    animatedContentScope: AnimatedContentScope,
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    val onUserClick: () -> Unit = remember(isLoggedIn) {
        {
            if (isLoggedIn) {
                expanded = !expanded
            }
        }
    }

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

            Box(modifier = Modifier) {
                IconButton(onClick = onUserClick) {
                    if (userImg.isEmpty()) {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = "User"
                        )
                    } else {
                        AsyncImage(
                            model = userImg,
                            contentDescription = "User Avatar",
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(text = "Log out")
                        },
                        onClick = {
                            onLogOutClick()
                            expanded = false
                        }
                    )

                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "Delete Account",
                                color = MaterialTheme.colorScheme.error
                            )
                        },
                        onClick = {
                            onDeleteAccountClick()
                            expanded = false
                        }
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}
