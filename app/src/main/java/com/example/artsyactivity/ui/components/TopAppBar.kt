package com.example.artsyactivity.ui.components

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    onSearchClick: () -> Unit = {},
    onUserClick: () -> Unit = {}
) {
    TopAppBar(
        title = { Text("Artist Search") },
        actions = {
            IconButton(onClick = onSearchClick) {
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
