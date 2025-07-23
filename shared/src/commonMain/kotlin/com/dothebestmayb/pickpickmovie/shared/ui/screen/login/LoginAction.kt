package com.dothebestmayb.pickpickmovie.shared.ui.screen.login

sealed interface LoginAction {

    data class OnIdChanged(val id: String) : LoginAction
    data class OnPwChanged(val pw: String) : LoginAction
    data object OnLoginClick: LoginAction
    data object OnRegisterClick: LoginAction
}
