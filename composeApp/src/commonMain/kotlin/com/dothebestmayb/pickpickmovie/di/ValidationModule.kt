package com.dothebestmayb.pickpickmovie.di

import com.dothebestmayb.pickpickmovie.core.validation.InputFieldValidator
import com.dothebestmayb.pickpickmovie.core.validation.Validator
import org.koin.dsl.module

val validationModule = module {

    single<Validator> {
        InputFieldValidator()
    }
}