package com.example.artsyactivity.ui.screens.art_info.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.sharp.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.example.artsyactivity.data.network.models.response.category.Category

@Composable
fun CategoryDialog(
    categories: List<Category>,
    onDismiss: () -> Unit
) {
    var currentIndex by remember { mutableIntStateOf(0) }
    val current = categories.getOrNull(currentIndex)

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        },
        title = {
            Text("Categories", style = MaterialTheme.typography.titleLarge)
        },
        text = {
            current?.let { category ->
                Box(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(category.img_src),
                            contentDescription = category.name,
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .clip(MaterialTheme.shapes.medium)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = category.name,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Box(modifier = Modifier.height(180.dp)) {
                            Text(
                                text = category.description,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier
                                    .padding(horizontal = 40.dp)
                                    .verticalScroll(rememberScrollState())
                                    .fillMaxSize()
                            )

                            IconButton(
                                onClick = {
                                    currentIndex = if (currentIndex > 0) currentIndex - 1 else categories.lastIndex
                                },
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .size(36.dp)
                                    .clip(CircleShape)
                            ) {
                                Icon(Icons.AutoMirrored.Sharp.KeyboardArrowLeft, contentDescription = "Previous")
                            }

                            IconButton(
                                onClick = {
                                    currentIndex = if (currentIndex < categories.lastIndex) currentIndex + 1 else 0
                                },
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .size(36.dp)
                                    .clip(CircleShape)
                            ) {
                                Icon(Icons.AutoMirrored.Sharp.KeyboardArrowRight, contentDescription = "Next")
                            }

                        }
                    }
                }
            } ?: Text("No categories found.")
        }
    )
}
