package com.dothebestmayb.pickpickmovie.data.auth

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val email: String,
    val password: String,
    val nickname: String,
    val registerCode: String,
)
