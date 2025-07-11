package com.dothebestmayb.pickpickmovie.ui.screen.common

import com.dothebestmayb.pickpickmovie.core.validation.ValidationRule

data class FieldState(
    val value: String = "",
    val isError: Boolean = false,
    val rule: ValidationRule = ValidationRule.None,
)
