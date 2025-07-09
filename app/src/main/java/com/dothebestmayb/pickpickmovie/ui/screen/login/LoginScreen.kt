package com.dothebestmayb.pickpickmovie.ui.screen.login

import android.widget.Toast
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dothebestmayb.pickpickmovie.R
import com.dothebestmayb.pickpickmovie.core.validation.InputFieldType
import com.dothebestmayb.pickpickmovie.designsystem.InputTextField
import com.dothebestmayb.pickpickmovie.ui.common.ObserveAsEvents
import com.dothebestmayb.pickpickmovie.ui.screen.common.FieldState
import com.dothebestmayb.pickpickmovie.ui.theme.ActionButtonColor
import com.dothebestmayb.pickpickmovie.ui.theme.ActionButtonContentColor
import com.dothebestmayb.pickpickmovie.ui.theme.PickPickMovieTheme

@Composable
fun LoginScreenRoot(
    modifier: Modifier = Modifier,
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            LoginEvent.LoginFail -> {
                Toast.makeText(
                    context,
                    R.string.login_fail,
                    Toast.LENGTH_LONG
                ).show()
            }

            LoginEvent.LoginSuccess -> {
                Toast.makeText(
                    context,
                    R.string.login_success,
                    Toast.LENGTH_LONG
                ).show()
                onLoginSuccess()
            }

            LoginEvent.RegisterClick -> {
                onRegisterClick()
            }

            LoginEvent.LoginAlreadyInProgress -> {
                Toast.makeText(
                    context,
                    R.string.login_already_in_progress,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

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
    val emailFieldState = state.fields[InputFieldType.Email] ?: FieldState()
    val passwordFieldState = state.fields[InputFieldType.Password] ?: FieldState()

    Scaffold(
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
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

            InputTextField(
                text = emailFieldState.value,
                label = stringResource(R.string.id_label),
                onTextChanged = {
                    onAction(LoginAction.OnIdChanged(it))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                validationRule = emailFieldState.rule,
                isError = emailFieldState.isError,
            )

            InputTextField(
                text = passwordFieldState.value,
                label = stringResource(R.string.pw_label),
                onTextChanged = {
                    onAction(LoginAction.OnPwChanged(it))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.padding(top = 18.dp),
                isPassword = true,
                validationRule = passwordFieldState.rule,
                isError = passwordFieldState.isError,
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 36.dp)
                    .padding(top = 24.dp),
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
                    .padding(top = 24.dp),
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
