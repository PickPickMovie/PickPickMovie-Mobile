package com.dothebestmayb.pickpickmovie.ui.screen.register

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
import androidx.compose.ui.focus.focusProperties
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
import com.dothebestmayb.pickpickmovie.designsystem.ButtonWithLoading
import com.dothebestmayb.pickpickmovie.designsystem.InputTextField
import com.dothebestmayb.pickpickmovie.ui.common.ObserveAsEvents
import com.dothebestmayb.pickpickmovie.ui.screen.common.FieldState
import com.dothebestmayb.pickpickmovie.ui.theme.ActionButtonColor
import com.dothebestmayb.pickpickmovie.ui.theme.ActionButtonContentColor
import com.dothebestmayb.pickpickmovie.ui.theme.PickPickMovieTheme

@Composable
fun RegisterScreenRoot(
    onRegisterSuccess: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is RegisterEvent.RegisterFail -> {
                Toast.makeText(
                    context,
                    R.string.register_fail,
                    Toast.LENGTH_LONG
                ).show()
            }

            RegisterEvent.RegisterSuccess -> {
                Toast.makeText(
                    context,
                    R.string.register_success,
                    Toast.LENGTH_LONG
                ).show()
                onRegisterSuccess()
            }
        }
    }

    RegisterScreen(
        state = state.value,
        onAction = viewModel::onAction,
        modifier = modifier,
    )
}

@Composable
fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val emailFieldState = state.fields[InputFieldType.Email] ?: FieldState()
    val passwordFieldState = state.fields[InputFieldType.Password] ?: FieldState()
    val passwordCheckFieldState = state.fields[InputFieldType.PasswordCheck] ?: FieldState()
    val nicknameFieldState = state.fields[InputFieldType.Nickname] ?: FieldState()
    val registerCodeFieldState = state.fields[InputFieldType.RegisterCode] ?: FieldState()

    Scaffold(
        modifier = modifier,
        containerColor = Color.White,
        bottomBar = {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(bottom = 32.dp)
            ) {
                ButtonWithLoading(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                        .focusProperties {
                            canFocus = true
                        },
                    onClick = {
                        onAction(RegisterAction.OnRegisterClick)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ActionButtonColor,
                        contentColor = ActionButtonContentColor,
                    ),
                    shape = RoundedCornerShape(corner = CornerSize(12.dp)),
                    enabled = state.isRegisterClickable,
                    isLoading = state.isActionHandling,
                ) {
                    Text(
                        text = stringResource(R.string.register)
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
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
                placeHolder = stringResource(R.string.id_placeHolder),
                label = stringResource(R.string.id_label),
                onTextChanged = {
                    onAction(RegisterAction.OnIdChanged(it))
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
                placeHolder = stringResource(R.string.pw_placeHolder),
                label = stringResource(R.string.pw_label),
                onTextChanged = {
                    onAction(RegisterAction.OnPwChanged(it))
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

            InputTextField(
                text = passwordCheckFieldState.value,
                label = stringResource(R.string.pw_check_label),
                onTextChanged = {
                    onAction(RegisterAction.OnPwCheckChanged(it))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.padding(top = 18.dp),
                isPassword = true,
                validationRule = passwordCheckFieldState.rule,
                isError = passwordCheckFieldState.isError,
            )

            InputTextField(
                text = nicknameFieldState.value,
                label = stringResource(R.string.nickname_label),
                placeHolder = stringResource(R.string.nickname_placeholder),
                onTextChanged = {
                    onAction(RegisterAction.OnNicknameChanged(it))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.padding(top = 18.dp),
                validationRule = nicknameFieldState.rule,
                isError = nicknameFieldState.isError,
            )

            InputTextField(
                text = registerCodeFieldState.value,
                label = stringResource(R.string.register_code_label),
                onTextChanged = {
                    onAction(RegisterAction.OnRegisterCodeChanged(it))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.padding(top = 18.dp),
                validationRule = registerCodeFieldState.rule,
                isError = registerCodeFieldState.isError,
            )
        }
    }
}

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
