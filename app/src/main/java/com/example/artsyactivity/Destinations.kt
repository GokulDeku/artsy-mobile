package com.example.artsyactivity

import kotlinx.serialization.Serializable

@Serializable
sealed interface Destinations {

    @Serializable
    data object HomeScreen: Destinations

    @Serializable
    data object SearchScreen: Destinations

    @Serializable
    data object ArtInfoScreen: Destinations
}