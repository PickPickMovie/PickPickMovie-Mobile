package com.dothebestmayb.pickpickmovie.di

import com.dothebestmayb.pickpickmovie.data.auth.di.storageModule
import org.koin.core.module.Module

actual fun platformModules(): List<Module> {
    return listOf(
        platformToastModule(),
        storageModule(),
    )
}