package com.dothebestmayb.pickpickmovie.data.session

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

class EncryptedSessionStorage @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : SessionStorage {

    override suspend fun get(): AuthInfo? {
        return withContext(Dispatchers.IO) {
            val json = sharedPreferences.getString(KEY_AUTH_INFO, null)
            json?.let {
                Json.decodeFromString<AuthInfoSerializable>(it).toAuthInfo()
            }
        }
    }

    override suspend fun set(info: AuthInfo?) {
        withContext(Dispatchers.IO) {
            if (info == null) {
                // IO 쓰레드로 전환했기 때문에 commit을 호출해도 상관 없음
                sharedPreferences.edit(commit = true) { remove(KEY_AUTH_INFO) }
                return@withContext
            }

            // json string으로 변환하기 위해 데이터 클래스는 serializable 해야 함
            val json = Json.encodeToString(info.toAuthInfoSerializable())
            sharedPreferences.edit(commit = true) {
                putString(KEY_AUTH_INFO, json)
            }
        }
    }

    companion object {
        private const val KEY_AUTH_INFO = "KEY_AUTH_INFO"
    }
}
