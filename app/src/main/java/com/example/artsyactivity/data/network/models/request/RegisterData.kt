package com.example.artsyactivity.data.network.models.request

import kotlinx.serialization.Serializable

@Serializable
data class RegisterData(
    val fullName: String,
    val email: String,
    val password: String
)