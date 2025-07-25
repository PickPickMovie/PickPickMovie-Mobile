package com.dothebestmayb.pickpickmovie.data.user.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class UserProfileResponseDto(
    val email: String,
    val nickname: String,
    val roles: Set<String>,
)
