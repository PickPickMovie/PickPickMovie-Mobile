package com.dothebestmayb.pickpickmovie.data.auth.di

import com.dothebestmayb.pickpickmovie.data.storage.KeychainSessionStorage
import com.dothebestmayb.pickpickmovie.data.storage.SessionStorage
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun storageModule(): Module = module {
    single<SessionStorage> {
        KeychainSessionStorage()
    }
}

