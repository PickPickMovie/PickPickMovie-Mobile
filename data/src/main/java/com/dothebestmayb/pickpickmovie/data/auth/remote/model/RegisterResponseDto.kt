package com.dothebestmayb.pickpickmovie.data.auth.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponseDto(
    val accessToken: String,
    val refreshToken: String,
)
