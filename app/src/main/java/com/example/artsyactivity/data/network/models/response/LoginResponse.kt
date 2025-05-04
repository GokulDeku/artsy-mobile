package com.example.artsyactivity.data.network.models.response

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val message: String,
    val user: UserData
)

@Serializable
data class UserData(
    val userId: String,
    val fullName: String,
    val userImg: String,
    val favoriteArtists: List<FavoriteArtist>? = emptyList()
)
