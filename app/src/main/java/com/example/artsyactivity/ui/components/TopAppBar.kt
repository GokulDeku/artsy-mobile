@file:OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)

package com.example.artsyactivity.ui.components

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier


@Composable
fun SharedTransitionScope.TopBar(
    isLoggedIn: Boolean,
    onSearchClick: () -> Unit,
    onUserClick: () -> Unit,
    animatedContentScope: AnimatedContentScope,
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

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
                IconButton(
                    onClick = {
                        if(isLoggedIn) {
                            expanded = !expanded
                        }
                        onUserClick()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = "User"
                    )
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

                        }
                    )

                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "Delete Account",
                                color = MaterialTheme.colorScheme.error
                            )
                        },
                        onClick = {}
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}
