package com.example.artsyactivity.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

@get:Composable
val ArrowBackIcon
    get() = Icon(
        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
        contentDescription = "Back Arrow"
    )


