package com.example.artsyactivity

import kotlinx.serialization.Serializable

@Serializable
sealed interface Destinations {

    @Serializable
    data object HomeScreen: Destinations

    @Serializable
    data object LoginScreen: Destinations

    @Serializable
    data object RegisterScreen: Destinations

    @Serializable
    data object SearchScreen: Destinations

    @Serializable
    data class ArtInfoScreen(val artistName: String, val artistId: String): Destinations
}