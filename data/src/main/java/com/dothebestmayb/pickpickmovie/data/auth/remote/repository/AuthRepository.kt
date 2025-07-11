package com.dothebestmayb.pickpickmovie.data.auth.remote.repository

import com.dothebestmayb.pickpickmovie.data.model.AuthResult
import com.dothebestmayb.pickpickmovie.data.model.UserProfile

interface AuthRepository {

    suspend fun register(
        email: String,
        password: String,
        nickname: String,
        registerCode: String
    ): AuthResult<Unit>

    suspend fun login(email: String, password: String): AuthResult<Unit>
    suspend fun getUserProfile(): AuthResult<UserProfile>
}
