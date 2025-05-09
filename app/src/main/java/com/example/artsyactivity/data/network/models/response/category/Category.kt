package com.example.artsyactivity.data.network.models.response.category
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: String,
    val name: String,
    val img_src: String,
    val description: String
)