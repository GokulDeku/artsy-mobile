package com.example.artsyactivity.data.network.models.response.search

import kotlinx.serialization.Serializable

@Serializable
data class ArtistDetail(
    val artist_id: String,
    val title: String,
    val img_src: String,
    val isFavorite: Boolean = false
)
