package com.example.artsyactivity.data.network.models.response.artistInfo

import kotlinx.serialization.Serializable

@Serializable
data class ArtistInfo(
    val id: String,
    val name: String,
    val biography: String,
    val nationality: String,
    val birthday: String,
    val deathday: String,
    val img_src: String,
    val similarArtists: List<SimilarArtist> = listOf()
) {
    fun getBirthInfo(): String {
        return "$nationality, $birthday - $deathday"
    }
}

@Serializable
data class SimilarArtist(
    val artist_id: String,
    val title: String,
    val img_src: String,

    val isFavorite: Boolean = false
)
