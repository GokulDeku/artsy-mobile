package com.example.artsyactivity.service

import com.example.artsyactivity.data.network.models.response.artistInfo.ArtWork
import com.example.artsyactivity.data.network.models.response.artistInfo.ArtistInfo
import com.example.artsyactivity.data.network.models.response.artistInfo.FavoriteResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ArtInfoService {
    @GET("artists/{id}")
    suspend fun getArtistDetails(
        @Path("id") id: String,
        @Query("similar_artists") shouldShowSimilarArtist: Boolean
    ): Response<ArtistInfo>

    @GET("artworks/{id}")
    suspend fun getArtWorkCategories(
        @Path("id") artWorkId: String,
    ): Response<ArtWork>

    @PUT("favorites/{id}")
    suspend fun updateFavoriteArtist(
        @Path("id") artistId: String,
    ): Response<FavoriteResponse>
}