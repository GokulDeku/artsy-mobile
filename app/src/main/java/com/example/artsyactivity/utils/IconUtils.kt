package com.example.artsyactivity.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

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


