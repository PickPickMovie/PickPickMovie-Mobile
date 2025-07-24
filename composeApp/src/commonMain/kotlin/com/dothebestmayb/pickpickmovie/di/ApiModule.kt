package com.dothebestmayb.pickpickmovie.di

import com.dothebestmayb.pickpickmovie.data.auth.remote.service.AuthService
import com.dothebestmayb.pickpickmovie.data.auth.remote.service.AuthServiceImpl
import com.dothebestmayb.pickpickmovie.data.user.remote.service.UserService
import com.dothebestmayb.pickpickmovie.data.user.remote.service.UserServiceImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val apiModule = module {
    single<AuthService> {
        AuthServiceImpl(get(named(AUTH_HTTP_CLIENT)))
    }

    single<UserService> {
        UserServiceImpl(get(named(DEFAULT_HTTP_CLIENT)))
    }
}
