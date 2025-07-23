package com.dothebestmayb.pickpickmovie.data.auth.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.logging.Logger
import platform.Foundation.NSLog

actual fun platformLogger(): Logger {
    return object: Logger {
        override fun log(message: String) {
            NSLog("Logger: %@", message)
        }
    }
}

actual fun platformHttpEngine(): HttpClientEngine {
    return Darwin.create()
}