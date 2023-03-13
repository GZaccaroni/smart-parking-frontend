package it.unibolss.smartparking.presentation.screens.user.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import it.unibolss.smartparking.presentation.R
import it.unibolss.smartparking.presentation.common.appalert.AppAlertState
import it.unibolss.smartparking.presentation.common.appalert.Bind
import org.jetbrains.annotations.TestOnly
import org.koin.androidx.compose.koinViewModel

/**
 * UI of the login screen.
 * @param vm view model of login screen.
 */
@Composable
fun LoginScreen(
    vm: LoginScreenViewModel = koinViewModel()
) {
    val uiState by vm.uiState.collectAsState()
    val alertState by vm.alertState.collectAsState()

    LoginLayout(
        uiState = uiState,
        alertState = alertState,
        onEmailChange = { vm.setEmail(it) },
        onPasswordChange = { vm.setPassword(it) },
        onSubmit = { vm.submit() },
        onSignUpClick = { vm.signUp() }
    )
}

@Composable
@TestOnly
fun LoginLayout(
    uiState: LoginUiState,
    alertState: AppAlertState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onSignUpClick: () -> Unit
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    alertState.Bind(scaffoldState.snackbarHostState)

    Scaffold(scaffoldState = scaffoldState) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .padding(32.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(
                    10.dp,
                    alignment = Alignment.CenterVertically
                )
            ) {
                Text(
                    text = stringResource(R.string.screen_title_login),
                    fontSize = 40.sp,
                )

                EmailTextField(
                    uiState = uiState,
                    onEmailChange = onEmailChange
                )
                PasswordTextField(
                    uiState = uiState,
                    onPasswordChange = onPasswordChange
                )
                SubmitButton(
                    uiState = uiState,
                    onSubmit = onSubmit
                )

                SignUpButton(
                    uiState = uiState,
                    onSignUpClick = onSignUpClick
                )
            }
        }
    }
}

@Composable
private fun SignUpButton(
    uiState: LoginUiState,
    onSignUpClick: () -> Unit
) {
    TextButton(
        modifier = Modifier.fillMaxWidth(),
        enabled = !uiState.loading,
        onClick = onSignUpClick
    ) {
        Text(stringResource(R.string.go_to_sign_up_cta), textAlign = TextAlign.Center)
    }
}

@Composable
private fun SubmitButton(
    uiState: LoginUiState,
    onSubmit: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    Button(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth(),
        onClick = {
            focusManager.clearFocus()
            onSubmit()
        },
        enabled = uiState.submitEnabled && !uiState.loading,
    ) {
        if (!uiState.loading) {
            Text(text = stringResource(R.string.login_cta))
        } else {
            CircularProgressIndicator(modifier = Modifier.size(32.dp))
        }
    }
}

@Composable
private fun EmailTextField(
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = uiState.email,
        onValueChange = onEmailChange,
        enabled = !uiState.loading,
        leadingIcon = { Icon(Icons.Rounded.Email, contentDescription = null) },
        label = { Text(stringResource(R.string.user_email)) },
        isError = uiState.isEmailError,
        textStyle = TextStyle(fontSize = 18.sp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )
}

@Composable
private fun PasswordTextField(
    uiState: LoginUiState,
    onPasswordChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = uiState.password,
        onValueChange = onPasswordChange,
        enabled = !uiState.loading,
        leadingIcon = { Icon(Icons.Rounded.Lock, contentDescription = null) },
        label = { Text(stringResource(R.string.user_password)) },
        isError = uiState.isPasswordError,
        textStyle = TextStyle(fontSize = 18.sp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation(),
    )
}
