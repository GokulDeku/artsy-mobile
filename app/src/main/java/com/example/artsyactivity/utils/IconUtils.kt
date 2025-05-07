package com.example.artsyactivity.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun ArrowBackIcon(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
            contentDescription = "Back Arrow"
        )
    }
}

@Composable
fun ChevronRightIcon() {
    Icon(
        imageVector = Icons.Filled.ChevronRight,
        contentDescription = "Back Arrow"
    )
}

@Composable
fun FavoriteIcon(
    onClick: () -> Unit,
    isFavorite: Boolean
) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clickable(
                onClick = onClick
            )
    ) {
        Icon(
            modifier = Modifier.padding(10.dp),
            imageVector = if(!isFavorite) Icons.Outlined.StarOutline else Icons.Filled.Star,
            contentDescription = "Favorite Icon"
        )
    }
}


