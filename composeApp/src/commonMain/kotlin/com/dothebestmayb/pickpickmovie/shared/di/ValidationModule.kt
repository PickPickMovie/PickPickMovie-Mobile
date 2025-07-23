package com.dothebestmayb.pickpickmovie.shared.di

import com.dothebestmayb.pickpickmovie.shared.core.validation.InputFieldValidator
import com.dothebestmayb.pickpickmovie.shared.core.validation.Validator
import org.koin.dsl.module

val validationModule = module {

    single<Validator> {
        InputFieldValidator()
    }
}