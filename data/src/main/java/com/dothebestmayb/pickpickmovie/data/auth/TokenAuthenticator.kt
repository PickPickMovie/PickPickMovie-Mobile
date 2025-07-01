package com.dothebestmayb.pickpickmovie.data.auth

import com.dothebestmayb.pickpickmovie.data.auth.local.storage.SessionStorage
import com.dothebestmayb.pickpickmovie.data.auth.remote.model.RefreshRequestDto
import com.dothebestmayb.pickpickmovie.data.model.AuthToken
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Provider

class TokenAuthenticator @Inject constructor(
    private val sessionStorage: SessionStorage,
    private val authServiceProvider: Provider<AuthService>,
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        synchronized(this) {
            return runBlocking {
                val authToken = sessionStorage.get()
                val oldAccessToken = response.request.header("Authorization")?.substring(7)

                // 다른 요청에 의해 토큰이 이미 갱신된 경우
                if (authToken != null && oldAccessToken != authToken.accessToken) {
                    return@runBlocking response.request.newBuilder()
                        .header("Authorization", "Bearer ${authToken.accessToken}")
                        .build()
                }

                val refreshToken = authToken?.refreshToken ?: return@runBlocking null

                try {
                    val refreshResponse =
                        authServiceProvider.get().refresh(RefreshRequestDto(refreshToken))

                    val newAuthToken = AuthToken(
                        accessToken = refreshResponse.accessToken,
                        refreshToken = refreshResponse.refreshToken,
                    )
                    sessionStorage.set(newAuthToken)

                    response.request.newBuilder()
                        .header("Authorization", "Bearer ${authToken.accessToken}")
                        .build()
                } catch (e: Exception) {
                    sessionStorage.set(null)
                    null
                }
            }
        }
    }
}
