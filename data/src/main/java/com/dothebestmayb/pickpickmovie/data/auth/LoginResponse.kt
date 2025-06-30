package com.dothebestmayb.pickpickmovie.data.auth

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
)
