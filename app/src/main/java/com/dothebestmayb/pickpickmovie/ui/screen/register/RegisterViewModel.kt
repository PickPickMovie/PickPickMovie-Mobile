package com.dothebestmayb.pickpickmovie.ui.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dothebestmayb.pickpickmovie.core.validation.InputFieldType
import com.dothebestmayb.pickpickmovie.core.validation.ValidationRule
import com.dothebestmayb.pickpickmovie.core.validation.Validator
import com.dothebestmayb.pickpickmovie.shared.data.auth.remote.repository.AuthRepository
import com.dothebestmayb.pickpickmovie.shared.data.model.AuthResult
import com.dothebestmayb.pickpickmovie.ui.screen.common.FieldState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepository: AuthRepository,
    private val validator: Validator,
) : ViewModel() {

    private val _state = MutableStateFlow(createInitialState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<RegisterEvent> { }
    val events = eventChannel.receiveAsFlow()

    private fun createInitialState(): RegisterState {
        return RegisterState(
            fields = mapOf(
                InputFieldType.Email to FieldState(rule = validator.getRule(InputFieldType.Email)),
                InputFieldType.Password to FieldState(rule = validator.getRule(InputFieldType.Password)),
                InputFieldType.PasswordCheck to FieldState(rule = validator.getRule(InputFieldType.PasswordCheck)),
                InputFieldType.Nickname to FieldState(rule = validator.getRule(InputFieldType.Nickname)),
                InputFieldType.RegisterCode to FieldState(rule = validator.getRule(InputFieldType.RegisterCode)),
            )
        )
    }

    fun onAction(action: RegisterAction) {
        when (action) {
            is RegisterAction.OnIdChanged -> {
                onFieldChanged(InputFieldType.Email, action.id)
            }

            is RegisterAction.OnNicknameChanged -> {
                onFieldChanged(InputFieldType.Nickname, action.nickname)
            }

            is RegisterAction.OnPwChanged -> {
                onFieldChanged(InputFieldType.Password, action.pw)
            }

            is RegisterAction.OnPwCheckChanged -> {
                onFieldChanged(InputFieldType.PasswordCheck, action.pwCheck)
            }

            RegisterAction.OnRegisterClick -> {
                if (_state.value.isActionHandling) {
                    return
                }
                register()
            }

            is RegisterAction.OnRegisterCodeChanged -> {
                onFieldChanged(InputFieldType.RegisterCode, action.registerCode)
            }
        }
    }

    private fun onFieldChanged(type: InputFieldType, newValue: String) {
        val currentField = _state.value.fields[type] ?: return
        val isError = when (val rule = currentField.rule) {
            is ValidationRule.InputField -> {
                if (type == InputFieldType.PasswordCheck) {
                    val passwordField = _state.value.fields[InputFieldType.Password] ?: return
                    passwordField.value != newValue
                } else {
                    newValue.isNotBlank() && !rule.validator(newValue)
                }
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

        val isRegisterClickable = newFields.values.all { it.value.isNotBlank() && !it.isError }

        _state.value = _state.value.copy(
            fields = newFields,
            isRegisterClickable = isRegisterClickable,
        )
    }

    private fun register() {
        _state.value = _state.value.copy(
            isActionHandling = true,
        )
        val email = _state.value.fields[InputFieldType.Email]?.value ?: ""
        val pw = _state.value.fields[InputFieldType.Password]?.value ?: ""
        val nickname = _state.value.fields[InputFieldType.Nickname]?.value ?: ""
        val registerCode = _state.value.fields[InputFieldType.RegisterCode]?.value ?: ""

        viewModelScope.launch {
            when (val result = authRepository.register(
                email = email,
                password = pw,
                nickname = nickname,
                registerCode = registerCode,
            )) {
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
