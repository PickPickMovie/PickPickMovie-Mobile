package com.dothebestmayb.pickpickmovie.data.auth.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class RefreshRequestDto(
    val refreshToken: String,
)
