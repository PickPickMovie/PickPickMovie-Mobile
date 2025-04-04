package com.dothebestmayb.pickpickmovie.ui.screen.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel: ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.OnIdChanged -> {
                _state.value = _state.value.copy(
                    id = action.id,
                    isLoginClickable = action.id.isNotBlank() && _state.value.pw.isNotBlank(),
                )
            }
            LoginAction.OnLoginClick -> {

            }
            is LoginAction.OnPwChanged -> {
                _state.value = _state.value.copy(
                    pw = action.pw,
                    isLoginClickable = action.pw.isNotBlank() && _state.value.id.isNotBlank(),
                )
            }
            LoginAction.OnRegisterClick -> {

            }
        }
    }
}
