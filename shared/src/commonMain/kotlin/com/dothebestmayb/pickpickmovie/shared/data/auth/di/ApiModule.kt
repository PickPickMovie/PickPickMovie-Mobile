package com.dothebestmayb.pickpickmovie.shared.data.auth.di

import com.dothebestmayb.pickpickmovie.shared.data.auth.remote.service.AuthService
import com.dothebestmayb.pickpickmovie.shared.data.auth.remote.service.AuthServiceImpl
import org.koin.dsl.module

val apiModule = module {
    single<AuthService> {
        AuthServiceImpl(get())
    }
}
