package com.dothebestmayb.pickpickmovie.data.auth

interface AuthRepository {

    suspend fun register(email: String, password: String, nickname: String, registerCode: String): AuthResult<Unit>
    suspend fun login(email: String, password: String): AuthResult<Unit>
    suspend fun authenticate(): AuthResult<Unit>
}
