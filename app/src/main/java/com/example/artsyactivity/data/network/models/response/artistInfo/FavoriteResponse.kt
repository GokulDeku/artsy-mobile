package com.example.artsyactivity.data.network.models.response.artistInfo

import kotlinx.serialization.Serializable

@Serializable
data class FavoriteResponse(
    val message: String,
    val isFavorite: Boolean,
    val artistData: ArtistData? = null
)

@Serializable
data class ArtistData(
    val id: String,
    val name: String,
    val nationality: String,
    val birthday: String,
    val deathday: String,
    val img_src: String,
    val addedAt: String
)
