package com.dothebestmayb.pickpickmovie.di

import com.dothebestmayb.pickpickmovie.core.validation.InputFieldValidator
import com.dothebestmayb.pickpickmovie.core.validation.Validator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ValidationModule {

    @Binds
    @Singleton
    abstract fun bindInputFieldValidator(
        validator: InputFieldValidator
    ): Validator
}
