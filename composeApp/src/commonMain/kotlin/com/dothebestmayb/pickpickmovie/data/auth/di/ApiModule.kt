package com.dothebestmayb.pickpickmovie.data.auth.di

import com.dothebestmayb.pickpickmovie.data.auth.remote.service.AuthService
import com.dothebestmayb.pickpickmovie.data.auth.remote.service.AuthServiceImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val apiModule = module {
    single<AuthService> {
        AuthServiceImpl(get(named(AUTH_HTTP_CLIENT)))
    }
}
