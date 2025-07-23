package com.dothebestmayb.pickpickmovie.shared.di

import com.dothebestmayb.pickpickmovie.shared.data.auth.di.apiModule
import com.dothebestmayb.pickpickmovie.shared.data.auth.di.authModule
import com.dothebestmayb.pickpickmovie.shared.data.auth.di.networkModule
import com.dothebestmayb.pickpickmovie.shared.data.auth.di.storageModule
import org.koin.core.module.Module

val commonModules: List<Module> = listOf(
    viewModelModule,
    validationModule,
    apiModule,
    authModule,
    networkModule,
    storageModule,
)