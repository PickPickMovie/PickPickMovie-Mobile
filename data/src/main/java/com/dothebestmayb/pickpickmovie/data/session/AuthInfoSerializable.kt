package com.dothebestmayb.pickpickmovie.data.session

import kotlinx.serialization.Serializable

@Serializable
data class AuthInfoSerializable(
    val accessToken: String,
    val refreshToken: String,
    val userId: String,
)
