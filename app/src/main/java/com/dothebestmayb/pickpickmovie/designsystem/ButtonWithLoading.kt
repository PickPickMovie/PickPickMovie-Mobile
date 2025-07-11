package com.dothebestmayb.pickpickmovie.designsystem

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dothebestmayb.pickpickmovie.ui.theme.PickPickMovieTheme

@Composable
fun ButtonWithLoading(
    onClick: () -> Unit,
    isLoading: Boolean,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    shape: Shape = ButtonDefaults.shape,
    composable: @Composable () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled && !isLoading,
        colors = colors,
        shape = shape,
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = ButtonDefaults.buttonColors().contentColor,
                strokeWidth = 3.dp,
            )
        } else {
            composable()
        }
    }
}

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

