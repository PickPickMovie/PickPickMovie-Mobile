package com.dothebestmayb.pickpickmovie.data.user.di

import com.dothebestmayb.pickpickmovie.data.user.remote.repository.UserRepository
import com.dothebestmayb.pickpickmovie.data.user.remote.repository.UserRepositoryImpl
import org.koin.dsl.module

val userModule = module {
    single<UserRepository> {
        UserRepositoryImpl(get())
    }
}
