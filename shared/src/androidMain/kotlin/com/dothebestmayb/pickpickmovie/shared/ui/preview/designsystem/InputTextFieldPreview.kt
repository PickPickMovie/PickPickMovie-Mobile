package com.dothebestmayb.pickpickmovie.shared.ui.preview.designsystem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dothebestmayb.pickpickmovie.shared.core.validation.InputFieldType
import com.dothebestmayb.pickpickmovie.shared.core.validation.InputPolicy
import com.dothebestmayb.pickpickmovie.shared.designsystem.InputTextField
import com.dothebestmayb.pickpickmovie.shared.ui.theme.PickPickMovieTheme

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
                    label = "이메일",
                    placeHolder = "3 ~ 6자",
                    onTextChanged = {
                        id = it
                    },
                    isError = true,
                    validationRule = InputPolicy.getRule(InputFieldType.Email)
                )
            }
        }
    }
}