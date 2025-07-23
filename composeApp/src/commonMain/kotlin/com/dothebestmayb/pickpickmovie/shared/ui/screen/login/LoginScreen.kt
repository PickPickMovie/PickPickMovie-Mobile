package com.dothebestmayb.pickpickmovie.shared.ui.screen.login

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dothebestmayb.pickpickmovie.shared.core.toast.ToastDurationType
import com.dothebestmayb.pickpickmovie.shared.core.toast.ToastManager
import com.dothebestmayb.pickpickmovie.shared.core.validation.InputFieldType
import com.dothebestmayb.pickpickmovie.shared.designsystem.ButtonWithLoading
import com.dothebestmayb.pickpickmovie.shared.designsystem.InputTextField
import com.dothebestmayb.pickpickmovie.shared.ui.common.ObserveAsEvents
import com.dothebestmayb.pickpickmovie.shared.ui.screen.common.FieldState
import com.dothebestmayb.pickpickmovie.shared.ui.theme.ActionButtonColor
import com.dothebestmayb.pickpickmovie.shared.ui.theme.ActionButtonContentColor
import com.dothebestmayb.pickpickmovie.shared.ui.theme.AppNameColor
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import pickpickmovie_mobile.composeapp.generated.resources.Res
import pickpickmovie_mobile.composeapp.generated.resources.app_name
import pickpickmovie_mobile.composeapp.generated.resources.id_label
import pickpickmovie_mobile.composeapp.generated.resources.login
import pickpickmovie_mobile.composeapp.generated.resources.login_already_in_progress
import pickpickmovie_mobile.composeapp.generated.resources.login_fail
import pickpickmovie_mobile.composeapp.generated.resources.login_success
import pickpickmovie_mobile.composeapp.generated.resources.pw_label
import pickpickmovie_mobile.composeapp.generated.resources.register

@Composable
fun LoginScreenRoot(
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = koinViewModel(),
    toastManager: ToastManager = koinInject(),
) {
    val coroutineScope = rememberCoroutineScope()
    val state = viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            LoginEvent.LoginFail -> coroutineScope.launch {
                toastManager.showToast(getString(Res.string.login_fail), ToastDurationType.LONG)
            }

            LoginEvent.LoginSuccess -> coroutineScope.launch {
                toastManager.showToast(getString(Res.string.login_success), ToastDurationType.LONG)
                onLoginSuccess()
            }

            LoginEvent.RegisterClick -> {
                onRegisterClick()
            }

            LoginEvent.LoginAlreadyInProgress -> coroutineScope.launch {
                toastManager.showToast(
                    getString(Res.string.login_already_in_progress),
                    ToastDurationType.LONG
                )
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
fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val emailFieldState = state.fields[InputFieldType.Email] ?: FieldState()
    val passwordFieldState = state.fields[InputFieldType.Password] ?: FieldState()

    Scaffold(
        modifier = modifier,
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(Res.string.app_name),
                color = AppNameColor,
                modifier = Modifier.padding(24.dp)
            )

            InputTextField(
                text = emailFieldState.value,
                label = stringResource(Res.string.id_label),
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
                label = stringResource(Res.string.pw_label),
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

            ButtonWithLoading(
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
                isLoading = state.isActionHandling,
            ) {
                Text(
                    text = stringResource(Res.string.login),
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
                enabled = !state.isActionHandling,
                shape = RoundedCornerShape(corner = CornerSize(12.dp)),
            ) {
                Text(
                    text = stringResource(Res.string.register),
                )
            }

        }

    }
}