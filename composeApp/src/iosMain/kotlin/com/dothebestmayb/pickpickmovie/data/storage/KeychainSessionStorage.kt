@file:OptIn(
    ExperimentalSettingsImplementation::class, ExperimentalSettingsApi::class,
    ExperimentalSerializationApi::class
)

package com.dothebestmayb.pickpickmovie.data.storage

import com.dothebestmayb.pickpickmovie.data.model.AuthToken
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ExperimentalSettingsImplementation
import com.russhwolf.settings.KeychainSettings
import com.russhwolf.settings.serialization.decodeValueOrNull
import com.russhwolf.settings.serialization.encodeValue
import kotlinx.serialization.ExperimentalSerializationApi

class KeychainSessionStorage : SessionStorage {

    private val settings = KeychainSettings(service = "com.dothebestmayb.pickpickmovie.session")

    override suspend fun get(): AuthToken? {
        return settings.decodeValueOrNull(AuthToken.serializer(), KEY_AUTH_INFO)
    }

    override suspend fun set(info: AuthToken?) {
        if (info == null) {
            settings.remove(KEY_AUTH_INFO)
        } else {
            settings.encodeValue(AuthToken.serializer(), KEY_AUTH_INFO, info)
        }
    }

    companion object {
        private const val KEY_AUTH_INFO = "KEY_AUTH_INFO"
    }
}