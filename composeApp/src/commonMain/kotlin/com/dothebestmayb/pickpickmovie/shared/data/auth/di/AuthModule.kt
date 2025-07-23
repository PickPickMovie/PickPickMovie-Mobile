package com.dothebestmayb.pickpickmovie.shared.data.auth.di

import com.dothebestmayb.pickpickmovie.shared.data.auth.remote.repository.AuthRepository
import com.dothebestmayb.pickpickmovie.shared.data.auth.remote.repository.AuthRepositoryImpl
import org.koin.dsl.module

val authModule = module {
    single<AuthRepository> {
        AuthRepositoryImpl(get(), get())
    }
}
