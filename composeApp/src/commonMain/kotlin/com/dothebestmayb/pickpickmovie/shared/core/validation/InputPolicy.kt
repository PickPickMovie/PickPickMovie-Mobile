package com.dothebestmayb.pickpickmovie.shared.core.validation

import androidx.compose.ui.text.input.KeyboardType
import pickpickmovie_mobile.composeapp.generated.resources.Res
import pickpickmovie_mobile.composeapp.generated.resources.error_email_format
import pickpickmovie_mobile.composeapp.generated.resources.error_nickname_format
import pickpickmovie_mobile.composeapp.generated.resources.error_password_format
import pickpickmovie_mobile.composeapp.generated.resources.error_pw_check_not_match
import pickpickmovie_mobile.composeapp.generated.resources.error_register_code_empty

object InputPolicy {

    private val emailAddressRegex = Regex(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    fun getRule(type: InputFieldType): ValidationRule {
        return when (type) {
            InputFieldType.Email -> ValidationRule.InputField(
                maxLength = 50,
                keyboardType = KeyboardType.Email,
                validator = { it.matches(emailAddressRegex) },
                errorMessageRes = Res.string.error_email_format,
            )

            InputFieldType.Password -> ValidationRule.InputField(
                maxLength = 16,
                keyboardType = KeyboardType.Password,
                validator = { it.length in 8..16 && it.any(Char::isLetter) && it.any(Char::isDigit) },
                errorMessageRes = Res.string.error_password_format,
            )

            InputFieldType.Nickname -> ValidationRule.InputField(
                maxLength = 8,
                keyboardType = KeyboardType.Text,
                validator = { it.length in 2..8 },
                errorMessageRes = Res.string.error_nickname_format,
            )

            InputFieldType.PasswordCheck -> ValidationRule.InputField(
                maxLength = 16,
                keyboardType = KeyboardType.Password,
                validator = { it.isNotEmpty() }, // 비밀번호 확인은 ViewModel에서 두 필드 값을 직접 비교해야 하므로, 여기서는 비었는지만 확인
                errorMessageRes = Res.string.error_pw_check_not_match,
            )

            InputFieldType.RegisterCode -> ValidationRule.InputField(
                maxLength = 32,
                keyboardType = KeyboardType.Text,
                validator = { it.isNotBlank() },
                errorMessageRes = Res.string.error_register_code_empty,
            )
        }
    }
}
