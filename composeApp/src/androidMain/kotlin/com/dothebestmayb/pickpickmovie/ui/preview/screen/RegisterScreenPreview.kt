package com.dothebestmayb.pickpickmovie.ui.preview.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.dothebestmayb.pickpickmovie.ui.screen.register.RegisterScreen
import com.dothebestmayb.pickpickmovie.ui.screen.register.RegisterState
import com.dothebestmayb.pickpickmovie.ui.theme.PickPickMovieTheme

@Preview
@Composable
private fun RegisterScreenPreview() {
    PickPickMovieTheme {
        RegisterScreen(
            state = RegisterState(
                fields = mapOf(),
            ),
            onAction = {},
        )
    }
}