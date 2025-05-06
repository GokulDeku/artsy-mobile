package com.example.artsyactivity.service

import com.example.artsyactivity.data.network.models.response.artistInfo.ArtistInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArtInfoService {
    @GET("artists/{id}")
    suspend fun getArtistDetails(
        @Path("id") id: String,
        @Query("similar_artist") shouldShowSimilarArtist: Boolean
    ): Response<ArtistInfo>
}