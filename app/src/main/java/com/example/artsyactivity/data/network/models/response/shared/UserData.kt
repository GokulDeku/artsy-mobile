package com.example.artsyactivity.data.network.models.response.shared

import com.example.artsyactivity.data.network.models.response.login.FavoriteArtist
import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val userId: String,
    val fullName: String = "",
    val userImg: String,
    val favoriteArtists: List<FavoriteArtist>? = emptyList()
)
