package com.dothebestmayb.pickpickmovie.data.user.remote.service

import com.dothebestmayb.pickpickmovie.data.user.remote.model.UserProfileResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType

class UserServiceImpl(
    private val client: HttpClient,
) : UserService {
    override suspend fun getUserProfile(): UserProfileResponseDto {
        return client.get("/auth/profile") {
            contentType(ContentType.Application.Json)
        }.body()
    }
}
