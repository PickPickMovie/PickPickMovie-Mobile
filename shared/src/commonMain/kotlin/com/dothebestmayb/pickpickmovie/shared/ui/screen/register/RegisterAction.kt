package com.dothebestmayb.pickpickmovie.shared.ui.screen.register

sealed interface RegisterAction {

    data class OnIdChanged(val id: String) : RegisterAction
    data class OnPwChanged(val pw: String) : RegisterAction
    data class OnPwCheckChanged(val pwCheck: String): RegisterAction
    data class OnNicknameChanged(val nickname: String): RegisterAction
    data class OnRegisterCodeChanged(val registerCode: String): RegisterAction
    data object OnRegisterClick: RegisterAction
}
