package com.example.artsyactivity.data.network.models.response

import kotlinx.serialization.Serializable

@Serializable
data class FavoriteArtist(
    val id: String,
    val name: String,
    val nationality: String,
    val birthday: String,
    val deathday: String,
    val img_src: String,
    val addedAt: String
) {
    fun getNationalityWithBirthYear(): String {
        return "$nationality ${birthday.split("/")[2]}"
    }
}