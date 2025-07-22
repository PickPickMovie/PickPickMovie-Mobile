package com.dothebestmayb.pickpickmovie.shared.data.auth.di

import com.dothebestmayb.pickpickmovie.shared.BuildConfig
import com.dothebestmayb.pickpickmovie.shared.data.auth.remote.model.RefreshRequestDto
import com.dothebestmayb.pickpickmovie.shared.data.auth.remote.service.AuthService
import com.dothebestmayb.pickpickmovie.shared.data.model.AuthToken
import com.dothebestmayb.pickpickmovie.shared.data.storage.SessionStorage
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.encodedPath
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

expect fun platformLogger(): Logger

expect fun platformHttpEngine(): HttpClientEngine

val networkModule = module {
    single<HttpClient> {
        createAuthenticatedClient(
            engine = get(),
            json = get(),
            logger = get(),
            sessionStorage = get(),
            authService = get(),
            baseUrl = BuildConfig.BASE_URL,
        )
    }

    single<Json> {
        Json {
            ignoreUnknownKeys = true
        }
    }

    single<Logger> {
        platformLogger()
    }

    single<HttpClientEngine> {
        platformHttpEngine()
    }
}

fun createAuthenticatedClient(
    engine: HttpClientEngine,
    json: Json,
    logger: Logger,
    sessionStorage: SessionStorage,
    authService: AuthService,
    baseUrl: String,
): HttpClient {
    return HttpClient(engine) {
        install(Logging) {
            this.logger = logger
            level = LogLevel.ALL
        }

        install(ContentNegotiation) {
            json(json)
        }

        defaultRequest {
            url(baseUrl)
        }

        install(Auth) {
            bearer {
                loadTokens {
                    sessionStorage.get()?.let {
                        BearerTokens(it.accessToken, it.refreshToken)
                    }
                }

                refreshTokens {
                    val refreshToken =
                        sessionStorage.get()?.refreshToken ?: return@refreshTokens null

                    try {
                        val response = authService.refresh(RefreshRequestDto(refreshToken))

                        val newAuthToken = AuthToken(
                            accessToken = response.accessToken,
                            refreshToken = response.refreshToken,
                        )
                        sessionStorage.set(newAuthToken)

                        BearerTokens(newAuthToken.accessToken, newAuthToken.refreshToken)
                    } catch (e: Exception) {
                        sessionStorage.set(null)
                        null
                    }
                }

                sendWithoutRequest { request ->
                    request.url.encodedPath.contains("/auth/refresh")
                }
            }
        }
    }
}
