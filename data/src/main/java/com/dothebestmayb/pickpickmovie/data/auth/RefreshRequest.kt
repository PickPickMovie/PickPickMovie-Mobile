package com.dothebestmayb.pickpickmovie.data.auth

import kotlinx.serialization.Serializable

@Serializable
data class RefreshRequest(
    val refreshToken: String,
)
