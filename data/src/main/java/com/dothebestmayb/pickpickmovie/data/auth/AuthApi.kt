package com.dothebestmayb.pickpickmovie.data.auth

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {

    @POST("/auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): RegisterResponse

    @POST("/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @GET("/auth/refresh")
    suspend fun refresh(
        @Body request: RefreshRequest
    ): LoginResponse


    // TODO : Interceptor 이용해서 Header에 AccessToken 넣도록 수정
    @GET("authenticate")
    suspend fun authenticate(
        @Header("Authorization") token: String,
    )
}
