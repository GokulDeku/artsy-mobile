package com.example.artsyactivity.data.network.models.response.account

import kotlinx.serialization.Serializable


@Serializable
data class DeleteAccountResponse(
    val message: String
)