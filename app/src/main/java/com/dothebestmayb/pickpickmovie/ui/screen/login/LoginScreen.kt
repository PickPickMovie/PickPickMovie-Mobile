package com.dothebestmayb.pickpickmovie.ui.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dothebestmayb.pickpickmovie.R
import com.dothebestmayb.pickpickmovie.ui.theme.ActionButtonColor
import com.dothebestmayb.pickpickmovie.ui.theme.ActionButtonContentColor
import com.dothebestmayb.pickpickmovie.ui.theme.PickPickMovieTheme

@Composable
fun LoginScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LoginScreen(
        state = state.value,
        onAction = viewModel::onAction,
        modifier = modifier,
    )
}

@Composable
private fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .background(Color.White)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(R.string.app_name),
                color = colorResource(R.color.app_name_color),
                modifier = Modifier.padding(24.dp)
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                value = state.id,
                onValueChange = {
                    onAction(LoginAction.OnIdChanged(it))
                },
                textStyle = MaterialTheme.typography.bodyLarge,
                label = {
                    Text(stringResource(R.string.id_label))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .padding(top = 36.dp),
                value = state.pw,
                onValueChange = {
                    onAction(LoginAction.OnPwChanged(it))
                },
                textStyle = MaterialTheme.typography.bodyLarge,
                label = {
                    Text(stringResource(R.string.pw_label))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 36.dp)
                    .padding(top = 36.dp),
                onClick = {
                    onAction(LoginAction.OnLoginClick)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = ActionButtonColor,
                    contentColor = ActionButtonContentColor,
                ),
                shape = RoundedCornerShape(corner = CornerSize(12.dp)),
                enabled = state.isLoginClickable,
            ) {
                Text(
                    text = stringResource(R.string.login)
                )
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 36.dp)
                    .padding(top = 36.dp),
                onClick = {
                    onAction(LoginAction.OnRegisterClick)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = ActionButtonColor,
                    contentColor = ActionButtonContentColor,
                ),
                shape = RoundedCornerShape(corner = CornerSize(12.dp)),
            ) {
                Text(
                    text = stringResource(R.string.register)
                )
            }

        }

    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    PickPickMovieTheme {
        LoginScreen(
            state = LoginState(),
            onAction = {},
        )
    }
}
