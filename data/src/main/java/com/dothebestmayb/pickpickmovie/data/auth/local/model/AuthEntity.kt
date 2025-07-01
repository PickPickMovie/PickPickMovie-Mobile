package com.dothebestmayb.pickpickmovie.data.auth.local.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthEntity(
    val accessToken: String,
    val refreshToken: String,
)
