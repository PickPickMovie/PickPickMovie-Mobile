package com.dothebestmayb.pickpickmovie.ui.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dothebestmayb.pickpickmovie.data.auth.AuthRepository
import com.dothebestmayb.pickpickmovie.data.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<RegisterEvent> { }
    val events = eventChannel.receiveAsFlow()

    init {
        observeStateChanges()
    }

    private fun observeStateChanges() {
        viewModelScope.launch {
            _state.collectLatest { currentState ->
                val isAllFieldsFilled = currentState.id.isNotBlank() &&
                        currentState.pw.isNotBlank() &&
                        currentState.pwCheck.isNotBlank() &&
                        currentState.nickname.isNotBlank() &&
                        currentState.registerCode.isNotBlank()

                if (currentState.isRegisterClickable != isAllFieldsFilled) {
                    _state.value = currentState.copy(
                        isRegisterClickable = isAllFieldsFilled
                    )
                }
            }
        }
    }

    fun onAction(action: RegisterAction) {

        when (action) {
            is RegisterAction.OnIdChanged -> {
                _state.value = _state.value.copy(
                    id = action.id,
                )
            }

            is RegisterAction.OnNicknameChanged -> {
                _state.value = _state.value.copy(
                    nickname = action.nickname,
                )
            }

            is RegisterAction.OnPwChanged -> {
                _state.value = _state.value.copy(
                    pw = action.pw,
                )
            }

            is RegisterAction.OnPwCheckChanged -> {
                _state.value = _state.value.copy(
                    pwCheck = action.pwCheck,
                )
            }

            RegisterAction.OnRegisterClick -> {
                register()
            }

            is RegisterAction.OnRegisterCodeChanged -> {
                _state.value = _state.value.copy(
                    registerCode = action.code,
                )
            }
        }
    }

    private fun register() {
        _state.value = _state.value.copy(
            isActionHandling = true,
        )
        viewModelScope.launch {
            when (val result = authRepository.register(_state.value.id, _state.value.pw, _state.value.nickname, _state.value.registerCode)) {
                is AuthResult.Authorized<Unit> -> {
                    eventChannel.send(RegisterEvent.RegisterSuccess)
                }

                is AuthResult.Conflict<Unit> -> {
                    eventChannel.send(RegisterEvent.RegisterFail(result.reason))
                }

                is AuthResult.Unauthorized<Unit> -> {
                    eventChannel.send(RegisterEvent.RegisterFail(""))
                }

                is AuthResult.UnknownError<Unit> -> {
                    eventChannel.send(RegisterEvent.RegisterFail(""))
                }
            }
            _state.value = _state.value.copy(
                isActionHandling = false,
            )
        }
    }
}
