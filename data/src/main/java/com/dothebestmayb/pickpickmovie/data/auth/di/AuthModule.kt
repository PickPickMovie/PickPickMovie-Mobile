package com.dothebestmayb.pickpickmovie.data.auth.di

import com.dothebestmayb.pickpickmovie.data.auth.local.storage.EncryptedSessionStorage
import com.dothebestmayb.pickpickmovie.data.auth.local.storage.SessionStorage
import com.dothebestmayb.pickpickmovie.data.auth.remote.repository.AuthRepository
import com.dothebestmayb.pickpickmovie.data.auth.remote.repository.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class AuthModule {

    @Binds
    abstract fun bindsAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindsSessionStorage(encryptedSessionStorage: EncryptedSessionStorage): SessionStorage
}
