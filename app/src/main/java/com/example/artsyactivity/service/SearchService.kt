package com.example.artsyactivity.service

import com.example.artsyactivity.data.network.models.response.SearchResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SearchService {
    @GET("search/{query}")
    suspend fun searchArtists(@Path("query") query: String): Response<SearchResult>
}