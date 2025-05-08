package com.example.artsyactivity.data.network.models.response.register

import com.example.artsyactivity.data.network.models.response.shared.UserData
import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    val message: String,
    val user: UserData
)