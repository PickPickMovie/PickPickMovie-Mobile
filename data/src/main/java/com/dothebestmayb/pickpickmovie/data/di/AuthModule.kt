package com.dothebestmayb.pickpickmovie.data.di

import com.dothebestmayb.pickpickmovie.data.auth.AuthRepository
import com.dothebestmayb.pickpickmovie.data.auth.AuthRepositoryImpl
import com.dothebestmayb.pickpickmovie.data.session.EncryptedSessionStorage
import com.dothebestmayb.pickpickmovie.data.session.SessionStorage
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
