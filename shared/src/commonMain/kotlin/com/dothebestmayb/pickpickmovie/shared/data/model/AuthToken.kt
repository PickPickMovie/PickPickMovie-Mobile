package com.dothebestmayb.pickpickmovie.shared.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthToken(
    val accessToken: String,
    val refreshToken: String,
)
