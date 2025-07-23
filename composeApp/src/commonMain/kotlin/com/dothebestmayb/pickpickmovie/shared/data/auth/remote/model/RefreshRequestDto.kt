package com.dothebestmayb.pickpickmovie.shared.data.auth.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class RefreshRequestDto(
    val refreshToken: String,
)
