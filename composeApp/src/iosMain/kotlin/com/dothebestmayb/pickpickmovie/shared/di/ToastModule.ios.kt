package com.dothebestmayb.pickpickmovie.shared.di

import com.dothebestmayb.pickpickmovie.shared.core.toast.IosToastManager
import com.dothebestmayb.pickpickmovie.shared.core.toast.ToastManager
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformToastModule(): Module = module {
     single<ToastManager> {
         IosToastManager()
     }
}