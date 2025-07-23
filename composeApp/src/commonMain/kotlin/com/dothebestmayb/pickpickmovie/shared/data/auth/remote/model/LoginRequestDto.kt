package com.dothebestmayb.pickpickmovie.shared.data.auth.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    val email: String,
    val password: String,
)
