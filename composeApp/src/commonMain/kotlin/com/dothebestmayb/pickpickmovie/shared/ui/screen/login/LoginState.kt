package com.dothebestmayb.pickpickmovie.shared.ui.screen.login

import com.dothebestmayb.pickpickmovie.shared.core.validation.InputFieldType
import com.dothebestmayb.pickpickmovie.shared.ui.screen.common.FieldState

data class LoginState(
    val fields: Map<InputFieldType, FieldState>,
    val isLoginClickable: Boolean = false,
    val isActionHandling: Boolean = false,
)
