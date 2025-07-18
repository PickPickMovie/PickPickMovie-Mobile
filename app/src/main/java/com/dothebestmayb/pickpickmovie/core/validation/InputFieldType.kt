package com.dothebestmayb.pickpickmovie.core.validation

sealed interface InputFieldType {
    data object Email : InputFieldType
    data object Password : InputFieldType
    data object PasswordCheck : InputFieldType
    data object Nickname : InputFieldType
    data object RegisterCode : InputFieldType
}
