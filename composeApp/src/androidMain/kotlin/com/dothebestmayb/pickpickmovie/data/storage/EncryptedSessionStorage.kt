package com.dothebestmayb.pickpickmovie.data.storage

import android.content.SharedPreferences
import androidx.core.content.edit
import com.dothebestmayb.pickpickmovie.data.auth.local.model.AuthEntity
import com.dothebestmayb.pickpickmovie.data.auth.mapper.toDomain
import com.dothebestmayb.pickpickmovie.data.auth.mapper.toEntity
import com.dothebestmayb.pickpickmovie.data.model.AuthToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class EncryptedSessionStorage(
    private val sharedPreferences: SharedPreferences,
) : SessionStorage {

    override suspend fun get(): AuthToken? {
        return withContext(Dispatchers.IO) {
            val json = sharedPreferences.getString(KEY_AUTH_INFO, null)
            json?.let {
                Json.decodeFromString<AuthEntity>(it).toDomain()
            }
        }
    }

    override suspend fun set(info: AuthToken) {
        withContext(Dispatchers.IO) {
            // json string으로 변환하기 위해 데이터 클래스는 serializable 해야 함
            val json = Json.encodeToString(info.toEntity())
            sharedPreferences.edit(commit = true) {
                putString(KEY_AUTH_INFO, json)
            }
        }
    }

    override suspend fun clear() {
        withContext(Dispatchers.IO) {
            sharedPreferences.edit(commit = true) { remove(KEY_AUTH_INFO) }
        }
    }

    companion object {
        private const val KEY_AUTH_INFO = "KEY_AUTH_INFO"
    }
}
