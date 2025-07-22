package com.dothebestmayb.pickpickmovie.shared.data.auth.remote.service

import com.dothebestmayb.pickpickmovie.shared.data.auth.remote.model.LoginRequestDto
import com.dothebestmayb.pickpickmovie.shared.data.auth.remote.model.LoginResponseDto
import com.dothebestmayb.pickpickmovie.shared.data.auth.remote.model.RefreshRequestDto
import com.dothebestmayb.pickpickmovie.shared.data.auth.remote.model.RegisterRequestDto
import com.dothebestmayb.pickpickmovie.shared.data.auth.remote.model.RegisterResponseDto
import com.dothebestmayb.pickpickmovie.shared.data.auth.remote.model.UserProfileResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthServiceImpl(
    private val client: HttpClient,
): AuthService {
    override suspend fun register(request: RegisterRequestDto): RegisterResponseDto {
        return client.post("/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    override suspend fun login(request: LoginRequestDto): LoginResponseDto {
        return client.post("/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    override suspend fun refresh(request: RefreshRequestDto): LoginResponseDto {
        return client.post("/auth/refresh") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    override suspend fun getUserProfile(): UserProfileResponseDto {
        return client.get("/auth/profile") {
            contentType(ContentType.Application.Json)
        }.body()
    }
}
