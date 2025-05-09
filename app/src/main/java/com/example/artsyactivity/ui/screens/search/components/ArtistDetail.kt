package com.example.artsyactivity.ui.screens.search.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.example.artsyactivity.R
import com.example.artsyactivity.utils.ChevronRightIcon
import com.example.artsyactivity.utils.FavoriteIcon
import toArtistImage

@Composable
fun ArtistDetail(
    modifier: Modifier = Modifier,
    artistName: String = "Artist Name",
    imageUrl: String = "",
    shouldShowFavoriteIcon: Boolean = true,
    isFavorite: Boolean = false,
    onFavoriteIconClicked: () -> Unit = { },
    onCardClick: () -> Unit = {}
) {

    val isInspectionMode = LocalInspectionMode.current

    val resource = if (isInspectionMode) {
        painterResource(R.drawable.artsy_logo)
    } else {
        val resolvedImage = imageUrl.toArtistImage()
        when (resolvedImage) {
            is Int -> painterResource(resolvedImage)
            is String -> rememberAsyncImagePainter(resolvedImage)
            else -> painterResource(R.drawable.artsy_logo)
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onCardClick)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {

                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    painter = resource,
                    contentDescription = "Artist Image",
                    contentScale = ContentScale.FillWidth
                )

        }

        if (shouldShowFavoriteIcon) {
            Box(modifier = Modifier.align(Alignment.TopEnd)) {
                FavoriteIcon(
                    isFavorite = isFavorite,
                    onClick = onFavoriteIconClicked
                )
            }
        }


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .clip(RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
                .background(MaterialTheme.colorScheme.primaryContainer.copy(0.75f))
        ) {
            Row(
                modifier = Modifier.padding(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = artistName,
                    style = MaterialTheme.typography.titleMedium
                )
                ChevronRightIcon()
            }
        }
    }
}
