package com.dothebestmayb.pickpickmovie.shared.data.auth.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestDto(
    val email: String,
    val password: String,
    val nickname: String,
    val registerCode: String,
)
