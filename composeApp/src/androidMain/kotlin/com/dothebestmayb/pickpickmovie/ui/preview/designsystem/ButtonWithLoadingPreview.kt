package com.dothebestmayb.pickpickmovie.ui.preview.designsystem

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.dothebestmayb.pickpickmovie.designsystem.ButtonWithLoading
import com.dothebestmayb.pickpickmovie.ui.theme.PickPickMovieTheme

@Preview
@Composable
private fun ButtonWithLoadingPreviewLoading() {
    PickPickMovieTheme {
        ButtonWithLoading(
            onClick = {},
            composable = {
                Text("확인")
            },
            enabled = false,
            isLoading = true,
        )
    }
}

@Preview
@Composable
private fun ButtonWithLoadingPreviewDefault() {
    PickPickMovieTheme {
        ButtonWithLoading(
            onClick = {},
            composable = {
                Text("확인")
            },
            enabled = true,
            isLoading = false,
        )
    }
}
