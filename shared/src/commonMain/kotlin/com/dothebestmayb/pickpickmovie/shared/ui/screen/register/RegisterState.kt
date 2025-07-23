package com.dothebestmayb.pickpickmovie.shared.ui.screen.register

import com.dothebestmayb.pickpickmovie.shared.core.validation.InputFieldType
import com.dothebestmayb.pickpickmovie.shared.ui.screen.common.FieldState

data class RegisterState(
    val fields: Map<InputFieldType, FieldState>,
    val isRegisterClickable: Boolean = false,
    val isActionHandling: Boolean = false,
)
