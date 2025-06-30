package com.dothebestmayb.pickpickmovie.data.auth

import com.dothebestmayb.pickpickmovie.data.session.AuthInfo
import com.dothebestmayb.pickpickmovie.data.session.SessionStorage
import retrofit2.HttpException
import java.net.HttpURLConnection
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val sessionStorage: SessionStorage,
) : AuthRepository {

    override suspend fun register(
        email: String,
        password: String,
        nickname: String,
        registerCode: String
    ): AuthResult<Unit> {
        return try {
            api.register(
                request = RegisterRequest(
                    email = email,
                    password = password,
                    nickname = nickname,
                    registerCode = registerCode,
                )
            )
            login(email, password)
        } catch (e: HttpException) {
            if (e.code() == 401) { // unauthorized
                AuthResult.Unauthorized()
            } else if (e.code() == HttpURLConnection.HTTP_CONFLICT) {
                AuthResult.Conflict(e.message())
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: IllegalStateException) {
            AuthResult.UnknownError()
        }
    }

    override suspend fun login(email: String, password: String): AuthResult<Unit> {
        return try {
            val response = api.login(
                request = LoginRequest(
                    email = email,
                    password = password,
                )
            )
            sessionStorage.set(
                AuthInfo(
                    accessToken = response.accessToken,
                    refreshToken = response.refreshToken,
                    userId = email,
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

    override suspend fun authenticate(): AuthResult<Unit> {
        return try {
            val info = sessionStorage.get() ?: return AuthResult.Unauthorized()
            api.authenticate("Bearer ${info.refreshToken}")

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
}
