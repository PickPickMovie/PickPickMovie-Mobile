package com.dothebestmayb.pickpickmovie.data.auth.di

import com.dothebestmayb.pickpickmovie.data.auth.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApiModule {

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

}
