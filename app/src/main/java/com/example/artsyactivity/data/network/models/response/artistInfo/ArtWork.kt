package com.example.artsyactivity.data.network.models.response.artistInfo

import kotlinx.serialization.Serializable

@Serializable
data class ArtWork(
    val count: Int,
    val data: List<ArtWorkDetail> = emptyList()
)

@Serializable
data class ArtWorkDetail(
    val id: String,
    val title: String,
    val date: String,
    val img_src: String
)
