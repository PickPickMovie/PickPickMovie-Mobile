package com.dothebestmayb.pickpickmovie.ui.screen.login

import com.dothebestmayb.pickpickmovie.core.validation.InputFieldType
import com.dothebestmayb.pickpickmovie.ui.screen.common.FieldState

data class LoginState(
    val fields: Map<InputFieldType, FieldState>,
    val isLoginClickable: Boolean = false,
    val isActionHandling: Boolean = false,
)
