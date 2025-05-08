package com.example.artsyactivity.data.network.models.response.login

import com.example.artsyactivity.data.network.models.response.shared.UserData
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val message: String,
    val user: UserData
)

