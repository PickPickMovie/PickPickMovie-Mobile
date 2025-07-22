package com.dothebestmayb.pickpickmovie.shared.data.auth.di

import com.dothebestmayb.pickpickmovie.shared.data.storage.SessionStorage
import org.koin.dsl.module

expect fun createSessionStorage(dependencies: PlatformDependencies): SessionStorage

expect class PlatformDependencies

val storageModule = module {
    single<SessionStorage> {
        createSessionStorage(get())
    }
}
