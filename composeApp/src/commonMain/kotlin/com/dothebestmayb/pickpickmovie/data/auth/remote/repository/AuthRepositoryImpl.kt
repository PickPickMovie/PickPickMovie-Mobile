package com.dothebestmayb.pickpickmovie.data.auth.remote.repository

import com.dothebestmayb.pickpickmovie.data.auth.remote.model.LoginRequestDto
import com.dothebestmayb.pickpickmovie.data.auth.remote.model.RegisterRequestDto
import com.dothebestmayb.pickpickmovie.data.auth.remote.service.AuthService
import com.dothebestmayb.pickpickmovie.data.model.AuthResult
import com.dothebestmayb.pickpickmovie.data.model.AuthToken
import com.dothebestmayb.pickpickmovie.data.storage.SessionStorage
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.http.HttpStatusCode

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
        } catch (e: ClientRequestException) { // 4xx
            if (e.response.status == HttpStatusCode.Unauthorized) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: ServerResponseException) { // 5xx
            AuthResult.UnknownError()
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }
}
