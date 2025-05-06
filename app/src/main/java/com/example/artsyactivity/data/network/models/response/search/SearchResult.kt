package com.example.artsyactivity.data.network.models.response.search

import kotlinx.serialization.Serializable

@Serializable
data class SearchResult(
    val count: Int,
    val data: List<ArtistDetail>
)
