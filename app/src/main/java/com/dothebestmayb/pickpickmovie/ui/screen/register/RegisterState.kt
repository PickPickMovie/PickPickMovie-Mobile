package com.dothebestmayb.pickpickmovie.ui.screen.register

data class RegisterState(
    val id: String = "",
    val pw: String = "",
    val pwCheck: String = "",
    val nickname: String = "",
    val registerCode: String = "",
    val isRegisterClickable: Boolean = false,
    val isActionHandling: Boolean = false,
)
