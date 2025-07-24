package com.dothebestmayb.pickpickmovie.di

import com.dothebestmayb.pickpickmovie.composeApp.BuildConfig
import com.dothebestmayb.pickpickmovie.data.auth.remote.model.RefreshRequestDto
import com.dothebestmayb.pickpickmovie.data.auth.remote.service.AuthService
import com.dothebestmayb.pickpickmovie.data.model.AuthToken
import com.dothebestmayb.pickpickmovie.data.storage.SessionStorage
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
import org.koin.core.qualifier.named
import org.koin.dsl.module

expect fun platformLogger(): Logger

expect fun platformHttpEngine(): HttpClientEngine

internal const val AUTH_HTTP_CLIENT = "AuthHttpClient"
internal const val DEFAULT_HTTP_CLIENT = "DefaultHttpClient"

val networkModule = module {
    single<HttpClient>(named(DEFAULT_HTTP_CLIENT)) {
        createAuthenticatedClient(
            engine = get(),
            json = get(),
            logger = get(),
            sessionStorage = get(),
            authService = get(),
            baseUrl = BuildConfig.BASE_URL,
        )
    }

    single<HttpClient>(named(AUTH_HTTP_CLIENT)) {
        HttpClient(get()) {
            install(ContentNegotiation) {
                json(get())
            }
            install(Logging) {
                this.logger = get()
                level = LogLevel.ALL
            }
            defaultRequest {
                url(BuildConfig.BASE_URL)
            }
        }
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

                // true일 경우, 서버로부터 401 응답을 받지 않아도 헤더에 jwt token을 추가한다.
                sendWithoutRequest { request ->
                    !request.url.encodedPath.contains("/auth/refresh")
                }
            }
        }

        install(Logging) {
            this.logger = logger
            level = LogLevel.ALL
        }
    }
}
