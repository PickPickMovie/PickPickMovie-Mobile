package com.dothebestmayb.pickpickmovie.data.session

data class AuthInfo(
    val accessToken: String,
    val refreshToken: String,
    val userId: String,
)
