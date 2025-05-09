package com.example.artsyactivity.ui.screens.art_info.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
                        // Image
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
                                    .padding(start = 8.dp)
                                    .size(36.dp)
                                    .clip(CircleShape)
                            ) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Previous")
                            }

                            IconButton(
                                onClick = {
                                    currentIndex = if (currentIndex < categories.lastIndex) currentIndex + 1 else 0
                                },
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .padding(end = 8.dp)
                                    .size(36.dp)
                                    .clip(CircleShape)
                            ) {
                                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next")
                            }

                        }
                    }
                }
            } ?: Text("No categories found.")
        }
    )
}
