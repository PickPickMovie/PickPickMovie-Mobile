package com.dothebestmayb.pickpickmovie.shared.data.auth.di

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.dothebestmayb.pickpickmovie.shared.data.storage.EncryptedSessionStorage
import com.dothebestmayb.pickpickmovie.shared.data.storage.SessionStorage
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

private const val PREFERENCES_FILE_NAME = "secure_session_prefs"

/**
 * Deprecated된 EncryptedSharedPreferences에 대한 대안은 아직 없는 듯 하다.
 *
 * https://proandroiddev.com/securing-the-future-navigating-the-deprecation-of-encrypted-shared-preferences-91ce3c20ae8d
 */
fun createSessionStorage(context: Context): SessionStorage {
    val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        PREFERENCES_FILE_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    return EncryptedSessionStorage(sharedPreferences)
}

actual fun storageModule(): Module = module {
    single<SessionStorage> {
        createSessionStorage(androidContext())
    }
}