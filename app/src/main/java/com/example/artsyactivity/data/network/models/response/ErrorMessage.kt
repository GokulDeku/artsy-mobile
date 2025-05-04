package com.example.artsyactivity.data.network.models.response

import kotlinx.serialization.Serializable

@Serializable
data class ErrorMessage(
    val message: String
)
