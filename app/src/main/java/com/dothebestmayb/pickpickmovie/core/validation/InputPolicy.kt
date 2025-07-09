package com.dothebestmayb.pickpickmovie.core.validation

import android.util.Patterns
import androidx.compose.ui.text.input.KeyboardType
import com.dothebestmayb.pickpickmovie.R

object InputPolicy {
    fun getRule(type: InputFieldType): ValidationRule {
        return when (type) {
            InputFieldType.Email -> ValidationRule.InputField(
                maxLength = 50,
                keyboardType = KeyboardType.Email,
                validator = { Patterns.EMAIL_ADDRESS.matcher(it).matches() },
                errorMessageResId = R.string.error_email_format,
            )
            InputFieldType.Password -> ValidationRule.InputField(
                maxLength = 16,
                keyboardType = KeyboardType.Password,
                validator = { it.length in 8..16 && it.any(Char::isLetter) && it.any(Char::isDigit) },
                errorMessageResId = R.string.error_password_format,
            )
            InputFieldType.Nickname -> ValidationRule.InputField(
                maxLength = 8,
                keyboardType = KeyboardType.Text,
                validator = { it.length in 2..8 },
                errorMessageResId = R.string.error_nickname_format,
            )
            InputFieldType.PasswordCheck -> ValidationRule.InputField(
                maxLength = 16,
                keyboardType = KeyboardType.Password,
                validator = { it.isNotEmpty() }, // 비밀번호 확인은 ViewModel에서 두 필드 값을 직접 비교해야 하므로, 여기서는 비었는지만 확인
                errorMessageResId = R.string.error_pw_check_not_match,
            )
            InputFieldType.RegisterCode -> ValidationRule.InputField(
                maxLength = 32,
                keyboardType = KeyboardType.Text,
                validator = { it.isNotBlank() },
                errorMessageResId = R.string.error_register_code_empty,
            )
        }
    }
}
