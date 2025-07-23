package com.dothebestmayb.pickpickmovie.shared.ui.screen.register

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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
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
import pickpickmovie_mobile.composeapp.generated.resources.id_placeHolder
import pickpickmovie_mobile.composeapp.generated.resources.nickname_label
import pickpickmovie_mobile.composeapp.generated.resources.nickname_placeholder
import pickpickmovie_mobile.composeapp.generated.resources.pw_check_label
import pickpickmovie_mobile.composeapp.generated.resources.pw_label
import pickpickmovie_mobile.composeapp.generated.resources.pw_placeHolder
import pickpickmovie_mobile.composeapp.generated.resources.register
import pickpickmovie_mobile.composeapp.generated.resources.register_code_label
import pickpickmovie_mobile.composeapp.generated.resources.register_fail
import pickpickmovie_mobile.composeapp.generated.resources.register_success

@Composable
fun RegisterScreenRoot(
    onRegisterSuccess: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = koinViewModel(),
    toastManager: ToastManager = koinInject(),
) {
    val coroutineScope = rememberCoroutineScope()
    val state = viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is RegisterEvent.RegisterFail -> coroutineScope.launch {
                toastManager.showToast(getString(Res.string.register_fail), ToastDurationType.LONG)
            }

            RegisterEvent.RegisterSuccess -> coroutineScope.launch {
                toastManager.showToast(
                    getString(Res.string.register_success),
                    ToastDurationType.LONG
                )
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
                        text = stringResource(Res.string.register),
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
                text = stringResource(Res.string.app_name),
                color = AppNameColor,
                modifier = Modifier.padding(24.dp)
            )

            InputTextField(
                text = emailFieldState.value,
                placeHolder = stringResource(Res.string.id_placeHolder),
                label = stringResource(Res.string.id_label),
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
                placeHolder = stringResource(Res.string.pw_placeHolder),
                label = stringResource(Res.string.pw_label),
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
                label = stringResource(Res.string.pw_check_label),
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
                label = stringResource(Res.string.nickname_label),
                placeHolder = stringResource(Res.string.nickname_placeholder),
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
                label = stringResource(Res.string.register_code_label),
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
