package com.example.artsyactivity.data.network.models.response

import kotlinx.serialization.Serializable

@Serializable
data class SearchResult(
    val count: Int,
    val data: List<ArtistDetail>
)

@Serializable
data class ArtistDetail(
    val artist_id: String,
    val title: String,
    val img_src: String
)
