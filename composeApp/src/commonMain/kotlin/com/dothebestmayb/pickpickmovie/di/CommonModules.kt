package com.dothebestmayb.pickpickmovie.di

import com.dothebestmayb.pickpickmovie.data.auth.di.authModule
import com.dothebestmayb.pickpickmovie.data.user.di.userModule
import org.koin.core.module.Module

val commonModules: List<Module> = listOf(
    viewModelModule,
    validationModule,
    apiModule,
    authModule,
    userModule,
    networkModule,
)
