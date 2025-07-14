package com.dothebestmayb.pickpickmovie.data.di

import com.dothebestmayb.pickpickmovie.data.auth.AuthInterceptor
import com.dothebestmayb.pickpickmovie.data.auth.TokenAuthenticator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val okhttpModule = module {
    single<OkHttpClient> {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(get<AuthInterceptor>())
            .addInterceptor(loggingInterceptor)
            .authenticator(get<TokenAuthenticator>())
            .build()
    }
}
