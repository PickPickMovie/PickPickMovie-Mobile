package com.dothebestmayb.pickpickmovie.shared.di

import org.koin.core.module.Module

actual fun platformModules(): List<Module> {
    return listOf(
        platformToastModule()
    )
}