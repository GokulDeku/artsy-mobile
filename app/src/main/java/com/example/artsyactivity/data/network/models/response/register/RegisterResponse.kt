package com.example.artsyactivity.data.network.models.response.register

import com.example.artsyactivity.data.network.models.response.login.FavoriteArtist
import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    val message: String,
    val user: UserData
)

@Serializable
data class UserData(
    val userId: String,
    val userImg: String,
    val favoriteArtists: List<FavoriteArtist>? = emptyList()
)