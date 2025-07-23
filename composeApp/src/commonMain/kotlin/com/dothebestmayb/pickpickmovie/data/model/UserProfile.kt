package com.dothebestmayb.pickpickmovie.data.model

data class UserProfile(
    val email: String,
    val nickname: String,
    val roles: Set<String>,
)
