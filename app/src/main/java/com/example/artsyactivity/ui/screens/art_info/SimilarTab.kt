package com.example.artsyactivity.ui.screens.art_info

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.artsyactivity.data.network.models.response.artistInfo.SimilarArtist
import com.example.artsyactivity.ui.screens.search.components.ArtistDetail

@Composable
fun SimilarTab(
    modifier: Modifier = Modifier,
    similarArtists: List<SimilarArtist>,
    onFavoriteIconClick: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .padding(top = 20.dp)
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        itemsIndexed(similarArtists) { index, item ->
            key(item.artist_id, item.isFavorite) {
                ArtistDetail(
                    artistName = item.title,
                    imageUrl = item.img_src,
                    isFavorite = item.isFavorite,
                    shouldShowFavoriteIcon = true,
                    onFavoriteIconClicked = {
                        onFavoriteIconClick(item.artist_id)
                    }
                )
            }
        }
    }
}