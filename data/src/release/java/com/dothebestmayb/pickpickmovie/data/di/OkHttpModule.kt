package com.dothebestmayb.pickpickmovie.data.di

import com.dothebestmayb.pickpickmovie.data.auth.AuthInterceptor
import com.dothebestmayb.pickpickmovie.data.auth.TokenAuthenticator
import okhttp3.OkHttpClient
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val okhttpModule = module {
    single<OkHttpClient> {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(get<AuthInterceptor>())
            .authenticator(get<TokenAuthenticator>())
            .build()
    }
}
