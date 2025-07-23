package com.dothebestmayb.pickpickmovie.shared.di

import com.dothebestmayb.pickpickmovie.shared.data.auth.di.storageModule
import org.koin.core.module.Module

actual fun platformModules(): List<Module> {
    return listOf(
        platformToastModule(),
        storageModule(),
    )
}