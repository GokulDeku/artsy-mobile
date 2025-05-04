package com.example.artsyactivity.service

import com.example.artsyactivity.data.network.models.request.LoginData
import com.example.artsyactivity.data.network.models.response.LoginResponse
import com.example.artsyactivity.data.network.models.response.LogoutResponse
import com.example.artsyactivity.data.network.models.response.SilentLoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {

    @POST("login")
    suspend fun login(@Body loginData: LoginData): Response<LoginResponse>

    @GET("me")
    suspend fun autoLogin(): Response<SilentLoginResponse>

    @POST("logout")
    suspend fun logout(): Response<LogoutResponse>
}