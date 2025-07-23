package com.dothebestmayb.pickpickmovie.di

import com.dothebestmayb.pickpickmovie.ui.screen.login.LoginViewModel
import com.dothebestmayb.pickpickmovie.ui.screen.main.MainViewModel
import com.dothebestmayb.pickpickmovie.ui.screen.register.RegisterViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainViewModel(get(), get())
    }
    viewModel {
        LoginViewModel(get(), get())
    }
    viewModel {
        RegisterViewModel(get(), get())
    }
}