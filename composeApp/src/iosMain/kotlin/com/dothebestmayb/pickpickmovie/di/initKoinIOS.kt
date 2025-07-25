package com.dothebestmayb.pickpickmovie.di

import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(commonModules + platformModules())
    }
}