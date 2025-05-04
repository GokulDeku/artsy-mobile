package com.example.artsyactivity.data.network.models.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginData(
    val email: String,
    val password: String
)