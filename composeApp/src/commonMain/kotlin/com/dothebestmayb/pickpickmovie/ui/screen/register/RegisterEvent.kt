package com.dothebestmayb.pickpickmovie.ui.screen.register

sealed interface RegisterEvent {

    data object RegisterSuccess: RegisterEvent
    data class RegisterFail(val reason: String): RegisterEvent
}
