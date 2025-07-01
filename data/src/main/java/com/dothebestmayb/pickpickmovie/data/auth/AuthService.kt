package com.dothebestmayb.pickpickmovie.data.auth

import com.dothebestmayb.pickpickmovie.data.auth.remote.model.LoginRequestDto
import com.dothebestmayb.pickpickmovie.data.auth.remote.model.LoginResponseDto
import com.dothebestmayb.pickpickmovie.data.auth.remote.model.RefreshRequestDto
import com.dothebestmayb.pickpickmovie.data.auth.remote.model.RegisterRequestDto
import com.dothebestmayb.pickpickmovie.data.auth.remote.model.RegisterResponseDto
import com.dothebestmayb.pickpickmovie.data.auth.remote.model.UserProfileResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {

    @POST("/auth/register")
    suspend fun register(
        @Body request: RegisterRequestDto
    ): RegisterResponseDto

    @POST("/auth/login")
    suspend fun login(
        @Body request: LoginRequestDto
    ): LoginResponseDto

    @GET("/auth/refresh")
    suspend fun refresh(
        @Body request: RefreshRequestDto
    ): LoginResponseDto

    @GET("/auth/profile")
    suspend fun getUserProfile(): UserProfileResponseDto
}
