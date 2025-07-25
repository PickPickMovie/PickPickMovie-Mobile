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

/**
 * KeyChain에 Key는 다음과 같이 저장 되어 있기 때문에, `settings.remove(KEY_AUTH_INFO)` 대신 clear 함수를 사용
 * ```
 * Key: [KEY_AUTH_INFO.accessToken, KEY_AUTH_INFO.refreshToken]
 * ```
 */
class KeychainSessionStorage : SessionStorage {

    private val settings = KeychainSettings(service = "com.dothebestmayb.pickpickmovie.session")

    override suspend fun get(): AuthToken? {
        return settings.decodeValueOrNull(AuthToken.serializer(), KEY_AUTH_INFO)
    }

    override suspend fun set(info: AuthToken) {
        settings.encodeValue(AuthToken.serializer(), KEY_AUTH_INFO, info)
    }

    override suspend fun clear() {
        settings.clear()
    }

    companion object {
        private const val KEY_AUTH_INFO = "KEY_AUTH_INFO"
    }
}