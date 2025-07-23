package com.dothebestmayb.pickpickmovie.ui.preview.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.dothebestmayb.pickpickmovie.core.validation.InputFieldType
import com.dothebestmayb.pickpickmovie.ui.screen.common.FieldState
import com.dothebestmayb.pickpickmovie.ui.screen.login.LoginScreen
import com.dothebestmayb.pickpickmovie.ui.screen.login.LoginState
import com.dothebestmayb.pickpickmovie.ui.theme.PickPickMovieTheme

@Preview
@Composable
private fun LoginScreenPreview() {
    PickPickMovieTheme {
        LoginScreen(
            state = LoginState(
                fields = mapOf(
                    InputFieldType.Email to FieldState(
                        value = "aaa@aaa.com",
                        isError = false,
                    ),
                    InputFieldType.Password to FieldState(
                        value = "q1w2e3",
                        isError = false,
                    )
                ),
                isLoginClickable = true,
                isActionHandling = false,
            ),
            onAction = {},
        )
    }
}
