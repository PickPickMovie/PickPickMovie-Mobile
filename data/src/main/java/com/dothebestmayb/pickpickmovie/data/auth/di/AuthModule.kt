package com.dothebestmayb.pickpickmovie.data.auth.di

import com.dothebestmayb.pickpickmovie.data.auth.AuthInterceptor
import com.dothebestmayb.pickpickmovie.data.auth.TokenAuthenticator
import com.dothebestmayb.pickpickmovie.data.auth.local.storage.EncryptedSessionStorage
import com.dothebestmayb.pickpickmovie.data.auth.local.storage.SessionStorage
import com.dothebestmayb.pickpickmovie.data.auth.remote.repository.AuthRepository
import com.dothebestmayb.pickpickmovie.data.auth.remote.repository.AuthRepositoryImpl
import org.koin.dsl.module

val authModule = module {
    single<AuthRepository> {
        AuthRepositoryImpl(get(), get())
    }

    single<SessionStorage> {
        EncryptedSessionStorage(get())
    }

    single<AuthInterceptor> {
        AuthInterceptor(lazy {
            get()
        })
    }

    single<TokenAuthenticator> {
        TokenAuthenticator(
            get(),
            lazy {
                get()
            }
        )
    }
}