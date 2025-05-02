package com.example.artsyactivity.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.artsyactivity.data.model.FavoriteArtist

@Composable
fun FavoriteArtistListItem(
    artist: FavoriteArtist,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(artist.name, style = MaterialTheme.typography.titleMedium)
                    Text(artist.getNationalityWithBirthYear(), style = MaterialTheme.typography.bodySmall)
                }
                Text(artist.addedTime, style = MaterialTheme.typography.bodySmall)
                IconButton(onClick = onClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "View Details"
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewFavoriteArtistItem() {
    val artist = FavoriteArtist(
        id = "1",
        name = "Calude Monet",
        nationality = "French",
        birthdate = "01/01/1840",
        addedTime = "4 seconds ago",
    )

    FavoriteArtistListItem(
        onClick = {

        },
        artist = artist
    )

}
