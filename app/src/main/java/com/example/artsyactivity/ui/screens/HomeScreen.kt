package com.example.artsyactivity.ui.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun HomeScreen(
    isLoggedIn: Boolean = false,
    favorites: List<FavoriteArtist> = emptyList(),
    onLoginClick: () -> Unit = {},
    onArtistClick: (FavoriteArtist) -> Unit = {}
) {
    val context = LocalContext.current

    Scaffold(
        topBar = { TopBar() }
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
                !isLoggedIn -> {
                    Button(
                        onClick = onLoginClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 100.dp, vertical = 10.dp),
                    ) {
                        Text("Log in to see favorites")
                    }
                }

                favorites.isEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No favorites")
                    }
                }

                else -> {
                    Text(
                        text = "Favorites",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
                    )
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 16.dp)
                    ) {
                        items(favorites, key = { it.id }) { artist ->
                            FavoriteArtistListItem(
                                artist = artist,
                                onClick = { onArtistClick(artist) }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
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

@Preview
@Composable
fun HomeScreenPreview_LoggedOut() {
    HomeScreen(
        isLoggedIn = false,
        favorites = emptyList(),
        onLoginClick = {},
        onArtistClick = {}
    )
}