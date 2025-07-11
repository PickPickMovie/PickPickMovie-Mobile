package com.dothebestmayb.pickpickmovie.core.validation

import androidx.annotation.StringRes
import androidx.compose.ui.text.input.KeyboardType


sealed interface ValidationRule {

    data object None : ValidationRule

    /**
     * @param maxLength 키보드 타입
     * @param keyboardType 키보드 타입
     * @param validator 유효성 검사 로직
     * @param errorMessageResId 유효성 검사 실패 시 보여줄 메시지 리소스 ID
     */
    data class InputField(
        val maxLength: Int,
        val keyboardType: KeyboardType,
        val validator: (String) -> Boolean,
        @StringRes val errorMessageResId: Int,
    ) : ValidationRule
}

