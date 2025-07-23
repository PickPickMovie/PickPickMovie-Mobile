package com.dothebestmayb.pickpickmovie.shared.ui.screen.common

import com.dothebestmayb.pickpickmovie.shared.core.validation.ValidationRule

data class FieldState(
    val value: String = "",
    val isError: Boolean = false,
    val rule: ValidationRule = ValidationRule.None,
)
