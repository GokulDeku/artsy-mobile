package com.example.artsyactivity.service

import com.example.artsyactivity.data.network.models.response.artistInfo.ArtWork
import com.example.artsyactivity.data.network.models.response.artistInfo.ArtistInfo
import com.example.artsyactivity.data.network.models.response.artistInfo.FavoriteResponse
import com.example.artsyactivity.data.network.models.response.category.CategoryResponse
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
    suspend fun getArtworkDetails(
        @Path("id") artWorkId: String,
    ): Response<ArtWork>

    @GET("categories/{artwork_id}")
    suspend fun getArtworkCategories(
        @Path("artwork_id") artworkId: String
    ): Response<CategoryResponse>

    @PUT("favorites/{id}")
    suspend fun updateFavoriteArtist(
        @Path("id") artistId: String,
    ): Response<FavoriteResponse>
}