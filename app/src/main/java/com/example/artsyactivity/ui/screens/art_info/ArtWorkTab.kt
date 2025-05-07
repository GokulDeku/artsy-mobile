package com.example.artsyactivity.ui.screens.art_info

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.example.artsyactivity.data.network.models.response.artistInfo.ArtWorkDetail

@Composable
fun ArtWorkTab(
    modifier: Modifier = Modifier,
    artWorks: List<ArtWorkDetail>
) {

    if(artWorks.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("No Artworks")
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            itemsIndexed(artWorks) { index, item ->
                ArtWorkItem(
                    item = item,
                    onViewCategoryClicked = {}
                )

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }

}

@Composable
fun ArtWorkItem(item: ArtWorkDetail, onViewCategoryClicked: () -> Unit) {
    Card {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = rememberAsyncImagePainter(item.img_src),
            contentDescription = item.title,
            contentScale = ContentScale.FillWidth
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${item.title}, ${item.date}",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Button(
                modifier = Modifier.padding(top = 10.dp),
                onClick = onViewCategoryClicked,
                content = {
                    Text(
                        text = "View categories"
                    )
                }
            )
        }
    }
}