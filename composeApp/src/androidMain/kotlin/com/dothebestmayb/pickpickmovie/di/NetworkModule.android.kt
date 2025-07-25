package com.dothebestmayb.pickpickmovie.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.logging.Logger
import timber.log.Timber

actual fun platformLogger(): Logger {
    return object: Logger {
        override fun log(message: String) {
            Timber.d(message)
        }
    }
}

actual fun platformHttpEngine(): HttpClientEngine {
    return OkHttp.create()
}
