package com.dothebestmayb.pickpickmovie.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.logging.Logger
import platform.Foundation.NSLog

actual fun platformLogger(): Logger {
    return object: Logger {
        override fun log(message: String) {
            val logMessage = "Logger: $message"
            NSLog(logMessage)
        }
    }
}

actual fun platformHttpEngine(): HttpClientEngine {
    return Darwin.create()
}
