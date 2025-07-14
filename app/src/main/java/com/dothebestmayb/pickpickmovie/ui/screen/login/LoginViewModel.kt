package com.dothebestmayb.pickpickmovie.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dothebestmayb.pickpickmovie.core.validation.InputFieldType
import com.dothebestmayb.pickpickmovie.core.validation.ValidationRule
import com.dothebestmayb.pickpickmovie.core.validation.Validator
import com.dothebestmayb.pickpickmovie.data.auth.remote.repository.AuthRepository
import com.dothebestmayb.pickpickmovie.data.model.AuthResult
import com.dothebestmayb.pickpickmovie.ui.screen.common.FieldState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val validator: Validator,
) : ViewModel() {

    private val _state = MutableStateFlow(createInitialState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<LoginEvent> { }
    val events = eventChannel.receiveAsFlow()

    private fun createInitialState(): LoginState {
        return LoginState(
            fields = mapOf(
                InputFieldType.Email to FieldState(rule = validator.getRule(InputFieldType.Email)),
                InputFieldType.Password to FieldState(rule = validator.getRule(InputFieldType.Password))
            )
        )
    }

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.OnIdChanged -> {
                onFieldChanged(InputFieldType.Email, action.id)
            }

            LoginAction.OnLoginClick -> {
                login()
            }

            is LoginAction.OnPwChanged -> {
                onFieldChanged(InputFieldType.Password, action.pw)
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

    private fun onFieldChanged(type: InputFieldType, newValue: String) {
        val currentField = _state.value.fields[type] ?: return
        val isError = when (val rule = currentField.rule) {
            is ValidationRule.InputField -> {
                newValue.isNotBlank() && !rule.validator(newValue)
            }

            ValidationRule.None -> newValue.isNotBlank()
        }

        val currentFields = _state.value.fields
        val updatedFieldState = (currentFields[type] ?: return).copy(
            value = newValue,
            isError = isError
        )

        val newFields = currentFields.toMutableMap().apply {
            this[type] = updatedFieldState
        }

        val isLoginClickable = newFields.values.all { it.value.isNotBlank() && !it.isError }

        _state.value = _state.value.copy(
            fields = newFields,
            isLoginClickable = isLoginClickable,
        )
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
        val id = _state.value.fields[InputFieldType.Email]?.value ?: ""
        val pw = _state.value.fields[InputFieldType.Password]?.value ?: ""

        viewModelScope.launch {
            val result = authRepository.login(id, pw)
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
