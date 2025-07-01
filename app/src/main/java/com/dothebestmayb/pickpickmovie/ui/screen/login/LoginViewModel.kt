package com.dothebestmayb.pickpickmovie.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dothebestmayb.pickpickmovie.data.auth.remote.repository.AuthRepository
import com.dothebestmayb.pickpickmovie.data.model.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<LoginEvent> { }
    val events = eventChannel.receiveAsFlow()

    init {
        observeStateChanges()
    }

    private fun observeStateChanges() {
        viewModelScope.launch {
            _state.collectLatest { currentState ->
                val isAllFieldsFilled = currentState.id.isNotBlank() &&
                        currentState.pw.isNotBlank()

                if (currentState.isLoginClickable != isAllFieldsFilled) {
                    _state.value = currentState.copy(
                        isLoginClickable = isAllFieldsFilled
                    )
                }
            }
        }
    }

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.OnIdChanged -> {
                _state.value = _state.value.copy(
                    id = action.id,
                )
            }

            LoginAction.OnLoginClick -> {
                login()
            }

            is LoginAction.OnPwChanged -> {
                _state.value = _state.value.copy(
                    pw = action.pw,
                )
            }

            LoginAction.OnRegisterClick -> {
                // 이미 처리 중인 작업이 있으면, 추가 요청을 무시함
                if (_state.value.isActionHandling) {
                    return
                }
                viewModelScope.launch {
                    eventChannel.send(LoginEvent.RegisterClick)
                }
            }
        }
    }

    private fun login() {
        // 이미 처리 중인 작업이 있으면, 추가 요청을 무시함
        if (_state.value.isActionHandling) {
            viewModelScope.launch {
                eventChannel.send(LoginEvent.LoginAlreadyInProgress)
            }
            return
        }
        _state.value = _state.value.copy(
            isActionHandling = true,
        )
        viewModelScope.launch {
            val result = authRepository.login(_state.value.id, _state.value.pw)
            when (result) {
                is AuthResult.Authorized<Unit> -> {
                    eventChannel.send(LoginEvent.LoginSuccess)
                }

                is AuthResult.Conflict<Unit> -> {
                    // 로그인 과정에서 Conflict가 발생했다면 코드를 잘못 짠 것임
                    Unit
                }

                is AuthResult.Unauthorized<Unit> -> {
                    eventChannel.send(LoginEvent.LoginFail)
                }

                is AuthResult.UnknownError<Unit> -> {
                    eventChannel.send(LoginEvent.LoginFail)
                }
            }
            _state.value = _state.value.copy(
                isActionHandling = false,
            )
        }
    }
}
