package com.example.artsyactivity

import kotlinx.serialization.Serializable

@Serializable
sealed interface Destinations {

    @Serializable
    data object HomeScreen: Destinations
}