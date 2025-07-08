package com.dothebestmayb.pickpickmovie.designsystem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dothebestmayb.pickpickmovie.R
import com.dothebestmayb.pickpickmovie.ui.theme.PickPickMovieTheme

@Composable
fun InputTextField(
    text: String,
    label: String,
    onTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeHolder: String? = null,
    isPassword: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
    maxLength: Int = Int.MAX_VALUE,
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
            if (it.length <= maxLength) {
                onTextChanged(it)
            }
        },
        textStyle = MaterialTheme.typography.bodyLarge,
        placeholder = {
            if (placeHolder != null) {
                Text(
                    text = placeHolder,
                    color = Color(0xFFADB5BD) // 플레이스홀더 텍스트 색상
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
                    stringResource(R.string.hide_password)
                } else {
                    stringResource(R.string.show_password)
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
                        contentDescription = description,
                    )
                }
            }
        },
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            // 입력 필드의 배경색
            focusedContainerColor = Color(0xFFF7F8F9),
            unfocusedContainerColor = Color(0xFFF7F8F9),
            disabledContainerColor = Color(0xFFF7F8F9),

            // 입력 필드의 밑줄(Indicator)을 투명하게 만들어 보이지 않게 처리
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,

            // 커서 색상
            cursorColor = Color.Black,
            // 텍스트 색상
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
        ),
        singleLine = true,
        keyboardOptions = keyboardOptions,
        visualTransformation = if (isPassword && !passwordVisible) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        }
    )
}

@Preview(showBackground = true)
@Composable
fun InputTextFieldPreview() {
    PickPickMovieTheme {
        var id by remember { mutableStateOf("") }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp),
                verticalArrangement = Arrangement.Top
            ) {
                InputTextField(
                    text = id,
                    label = "아이디",
                    placeHolder = "3 ~ 6자",
                    onTextChanged = {
                        id = it
                    },
                    maxLength = 6
                )
            }
        }
    }
}
