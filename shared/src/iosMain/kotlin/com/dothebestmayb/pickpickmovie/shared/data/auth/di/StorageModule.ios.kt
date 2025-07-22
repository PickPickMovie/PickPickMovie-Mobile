package com.dothebestmayb.pickpickmovie.shared.data.auth.di

import com.dothebestmayb.pickpickmovie.shared.data.storage.KeychainSessionStorage
import com.dothebestmayb.pickpickmovie.shared.data.storage.SessionStorage

actual fun createSessionStorage(dependencies: PlatformDependencies): SessionStorage {
    return KeychainSessionStorage()
}

actual class PlatformDependencies

