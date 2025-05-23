package com.example.artsyactivity.service

import com.example.artsyactivity.data.network.models.request.LoginData
import com.example.artsyactivity.data.network.models.request.RegisterData
import com.example.artsyactivity.data.network.models.response.LogoutResponse
import com.example.artsyactivity.data.network.models.response.account.DeleteAccountResponse
import com.example.artsyactivity.data.network.models.response.login.LoginResponse
import com.example.artsyactivity.data.network.models.response.login.SilentLoginResponse
import com.example.artsyactivity.data.network.models.response.register.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {

    @POST("login")
    suspend fun login(@Body loginData: LoginData): Response<LoginResponse>

    @POST("register")
    suspend fun register(@Body registerData: RegisterData): Response<RegisterResponse>

    @GET("me")
    suspend fun autoLogin(): Response<SilentLoginResponse>

    @DELETE("me")
    suspend fun deleteAccount(): Response<DeleteAccountResponse>

    @POST("logout")
    suspend fun logout(): Response<LogoutResponse>
}