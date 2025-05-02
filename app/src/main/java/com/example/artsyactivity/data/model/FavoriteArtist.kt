package com.example.artsyactivity.data.model

data class FavoriteArtist(
    val id: String,
    val name: String,
    val nationality: String,
    val birthdate: String,
    val addedTime: String
) {
    fun getNationalityWithBirthYear(): String {
        return "$nationality ${birthdate.split("/")[2]}"
    }
}