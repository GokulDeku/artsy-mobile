package com.example.artsyactivity.data.network.models.response.category

import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    val count: Int,
    val data: List<Category>
)