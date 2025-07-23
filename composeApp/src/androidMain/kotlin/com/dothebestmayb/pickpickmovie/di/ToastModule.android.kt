package com.dothebestmayb.pickpickmovie.di

import com.dothebestmayb.pickpickmovie.core.toast.AndroidToastManager
import com.dothebestmayb.pickpickmovie.core.toast.ToastManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformToastModule(): Module = module {
    single<ToastManager> {
        AndroidToastManager(androidContext().applicationContext)
    }
}