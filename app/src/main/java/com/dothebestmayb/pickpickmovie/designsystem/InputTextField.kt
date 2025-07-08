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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dothebestmayb.pickpickmovie.R
import com.dothebestmayb.pickpickmovie.ui.theme.PickPickMovieTheme

@Composable
fun InputTextField(
    text: String,
    placeHolderText: String,
    onTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    isPasswordField: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    maxLength: Int = Int.MAX_VALUE,
) {

    var passwordVisible by remember { mutableStateOf(false) }

    TextField(
        value = text,
        onValueChange = {
            if (it.length <= maxLength) {
                onTextChanged(it)
            }
        },
        textStyle = MaterialTheme.typography.bodyLarge,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        placeholder = {
            Text(
                text = placeHolderText,
                color = Color(0xFFADB5BD)
            )
        },
        trailingIcon = {
            if (isPasswordField) {
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
            focusedContainerColor = Color(0xFFF7F8F9),
            unfocusedContainerColor = Color(0xFFF7F8F9),
            disabledContainerColor = Color(0xFFF7F8F9),

            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,

            cursorColor = Color.Black,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
        ),
        singleLine = true,
        keyboardOptions = keyboardOptions,
        visualTransformation = if (isPasswordField && !passwordVisible) {
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
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                InputTextField(
                    text = "text",
                    placeHolderText = "아이디",
                    onTextChanged = {},
                )

                InputTextField(
                    text = "12345",
                    placeHolderText = "비밀번호 (최대 10자)",
                    onTextChanged = {},
                    isPasswordField = true,
                    maxLength = 10
                )
            }
        }
    }
}
