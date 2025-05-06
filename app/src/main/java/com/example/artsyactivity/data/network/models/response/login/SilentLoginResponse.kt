package com.example.artsyactivity.data.network.models.response.login

import kotlinx.serialization.Serializable

@Serializable
data class SilentLoginResponse(
    val isAuthorized: Boolean,
    val userId: String,
    val fullName: String,
    val email: String,
    val userImg: String,
    val favoriteArtists: List<FavoriteArtist>
)