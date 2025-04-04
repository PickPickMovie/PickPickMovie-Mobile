package com.dothebestmayb.pickpickmovie.ui.screen.login

data class LoginState(
    val id: String = "",
    val pw: String = "",
    val isLoginClickable: Boolean = false,
    val isActionHandling: Boolean = false,
)
