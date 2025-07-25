package com.dothebestmayb.pickpickmovie.ui.screen.register

import com.dothebestmayb.pickpickmovie.core.validation.InputFieldType
import com.dothebestmayb.pickpickmovie.ui.screen.common.FieldState

data class RegisterState(
    val fields: Map<InputFieldType, FieldState>,
    val isRegisterClickable: Boolean = false,
    val isActionHandling: Boolean = false,
)
