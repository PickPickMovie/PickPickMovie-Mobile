package com.dothebestmayb.pickpickmovie.shared.designsystem

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.dothebestmayb.pickpickmovie.shared.core.validation.ValidationRule
import com.dothebestmayb.pickpickmovie.shared.ui.theme.InputTextFieldBackgroundColor
import com.dothebestmayb.pickpickmovie.shared.ui.theme.PlaceHolderColor
import org.jetbrains.compose.resources.stringResource
import pickpickmovie_mobile.shared.generated.resources.Res
import pickpickmovie_mobile.shared.generated.resources.hide_password
import pickpickmovie_mobile.shared.generated.resources.show_password

@Composable
fun InputTextField(
    text: String,
    onTextChanged: (String) -> Unit,
    label: String,
    validationRule: ValidationRule,
    isError: Boolean,
    modifier: Modifier = Modifier,
    placeHolder: String? = null,
    isPassword: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
) {
    var passwordVisible by remember { mutableStateOf(false) }

    TextField(
        modifier = modifier
            .fillMaxWidth() // 가로 너비를 꽉 채움
            .padding(horizontal = 32.dp), // 좌우 패딩 추가
        value = text, // 현재 텍스트 상태
        label = {
            Text(label)
        },
        onValueChange = {
            when (validationRule) {
                ValidationRule.None -> Unit
                is ValidationRule.InputField -> {
                    if (it.length <= validationRule.maxLength) {
                        onTextChanged(it)
                    }
                }
            }
        },
        textStyle = MaterialTheme.typography.bodyLarge,
        placeholder = {
            if (placeHolder != null) {
                Text(
                    text = placeHolder,
                    color = PlaceHolderColor
                )
            }
        },
        trailingIcon = {
            if (isPassword) {
                val image = if (passwordVisible) {
                    Icons.Filled.Visibility
                } else {
                    Icons.Filled.VisibilityOff
                }
                val description = if (passwordVisible) {
                    Res.string.hide_password
                } else {
                    Res.string.show_password
                }
                IconButton(
                    onClick = {
                        passwordVisible = !passwordVisible
                    },
                    modifier = Modifier.focusProperties {
                        canFocus = false
                    }
                ) {
                    Icon(
                        imageVector = image,
                        contentDescription = stringResource(description),
                    )
                }
            }
        },
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            // 입력 필드의 배경색
            focusedContainerColor = InputTextFieldBackgroundColor,
            unfocusedContainerColor = InputTextFieldBackgroundColor,
            disabledContainerColor = InputTextFieldBackgroundColor,

            // 입력 필드의 밑줄(Indicator)을 투명하게 만들어 보이지 않게 처리
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,

            // 커서 색상
            cursorColor = Color.Black,
            // 텍스트 색상
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,

            errorIndicatorColor = Color.Red
        ),
        singleLine = true,
        keyboardOptions = if (validationRule is ValidationRule.InputField) {
            keyboardOptions.copy(keyboardType = validationRule.keyboardType)
        } else {
            keyboardOptions
        },
        visualTransformation = if (isPassword && !passwordVisible) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        isError = isError,
        supportingText = {
            val errorMessage = if (isError && validationRule is ValidationRule.InputField) {
                stringResource(validationRule.errorMessageRes)
            } else {
                ""
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 24.dp) // supportingText와 무관하게 동일한 height 유지하기 위한 설정
                    .padding(top = 4.dp)
            ) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    )
}

