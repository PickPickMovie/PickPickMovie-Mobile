package com.dothebestmayb.pickpickmovie.shared.ui.screen.register

sealed interface RegisterEvent {

    data object RegisterSuccess: RegisterEvent
    data class RegisterFail(val reason: String): RegisterEvent
}
