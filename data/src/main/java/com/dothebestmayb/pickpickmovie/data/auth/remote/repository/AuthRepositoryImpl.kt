package com.dothebestmayb.pickpickmovie.data.auth.remote.repository

import com.dothebestmayb.pickpickmovie.data.auth.AuthService
import com.dothebestmayb.pickpickmovie.data.auth.local.storage.SessionStorage
import com.dothebestmayb.pickpickmovie.data.auth.mapper.toDomain
import com.dothebestmayb.pickpickmovie.data.auth.remote.model.LoginRequestDto
import com.dothebestmayb.pickpickmovie.data.auth.remote.model.RegisterRequestDto
import com.dothebestmayb.pickpickmovie.data.model.AuthResult
import com.dothebestmayb.pickpickmovie.data.model.AuthToken
import com.dothebestmayb.pickpickmovie.data.model.UserProfile
import retrofit2.HttpException

class AuthRepositoryImpl(
    private val api: AuthService,
    private val sessionStorage: SessionStorage,
) : AuthRepository {

    override suspend fun register(
        email: String,
        password: String,
        nickname: String,
        registerCode: String
    ): AuthResult<Unit> {
        return try {
            val response = api.register(
                request = RegisterRequestDto(
                    email = email,
                    password = password,
                    nickname = nickname,
                    registerCode = registerCode,
                )
            )
            sessionStorage.set(
                AuthToken(
                    accessToken = response.accessToken,
                    refreshToken = response.refreshToken,
                )
            )
            AuthResult.Authorized()
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }

    override suspend fun login(email: String, password: String): AuthResult<Unit> {
        return try {
            val response = api.login(
                request = LoginRequestDto(
                    email = email,
                    password = password,
                )
            )
            sessionStorage.set(
                AuthToken(
                    accessToken = response.accessToken,
                    refreshToken = response.refreshToken,
                )
            )
            AuthResult.Authorized()
        } catch (e: HttpException) {
            if (e.code() == 401) { // unauthorized
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }

    override suspend fun getUserProfile(): AuthResult<UserProfile> {
        return try {
            val userProfile = api.getUserProfile().toDomain()
            AuthResult.Authorized(userProfile)
        } catch (e: Exception) {
            AuthResult.Unauthorized()
        }
    }
}
