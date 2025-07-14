package com.dothebestmayb.pickpickmovie.data.auth.di

import com.dothebestmayb.pickpickmovie.data.auth.AuthService
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single<AuthService> {
        get<Retrofit>().create(AuthService::class.java)
    }
}
