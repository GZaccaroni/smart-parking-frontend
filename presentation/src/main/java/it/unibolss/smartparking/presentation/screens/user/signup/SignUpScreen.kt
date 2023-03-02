package it.unibolss.smartparking.presentation.screens.user.signup

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import it.unibolss.smartparking.presentation.R
import it.unibolss.smartparking.presentation.common.appalert.AppAlertState
import it.unibolss.smartparking.presentation.common.appalert.Bind
import org.jetbrains.annotations.TestOnly
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignUpScreen(
    vm: SignUpScreenViewModel = koinViewModel()
) {
    val uiState by vm.uiState.collectAsState()
    val alertState by vm.alertState.collectAsState()

    SignUpLayout(
        uiState = uiState,
        alertState = alertState,
        onNameChange = { vm.setName(it) },
        onEmailChange = { vm.setEmail(it) },
        onPasswordChange = { vm.setPassword(it) },
        onSubmit = { vm.submit() }
    )
}

@Composable
@TestOnly
fun SignUpLayout(
    uiState: SignUpUiState,
    alertState: AppAlertState,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit
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
                    text = stringResource(R.string.screen_title_sign_up),
                    fontSize = 40.sp,
                )
                NameTextField(uiState, onNameChange)
                EmailTextField(uiState, onEmailChange)
                PasswordTextField(uiState, onPasswordChange)

                SignUpButton(uiState, onSubmit)
            }
        }
    }
}

@Composable
private fun NameTextField(
    uiState: SignUpUiState,
    onNameChange: (String) -> Unit
) {
    OutlinedTextField(
        value = uiState.name,
        isError = uiState.isNameError,
        enabled = !uiState.loading,
        onValueChange = onNameChange,
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = { Icon(Icons.Rounded.Face, contentDescription = null) },
        label = { Text(stringResource(R.string.user_name)) },
        textStyle = TextStyle(fontSize = 18.sp),
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
    )
}

@Composable
private fun EmailTextField(
    uiState: SignUpUiState,
    onEmailChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = uiState.email,
        isError = uiState.isEmailError,
        enabled = !uiState.loading,
        onValueChange = onEmailChange,
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = { Icon(Icons.Rounded.Email, contentDescription = null) },
        label = { Text(stringResource(R.string.user_email)) },

        textStyle = TextStyle(fontSize = 18.sp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )
}

@Composable
private fun PasswordTextField(
    uiState: SignUpUiState,
    onPasswordChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = uiState.password,
        isError = uiState.isPasswordError,
        enabled = !uiState.loading,
        onValueChange = onPasswordChange,
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = { Icon(Icons.Rounded.Lock, contentDescription = null) },
        label = { Text(stringResource(R.string.user_password)) },

        textStyle = TextStyle(fontSize = 18.sp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation(),
    )
}

@Composable
private fun SignUpButton(
    uiState: SignUpUiState,
    onSubmit: () -> Unit
) {
    Button(
        enabled = uiState.submitEnabled && !uiState.loading,
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth(),
        onClick = onSubmit,
    ) {
        if (!uiState.loading) {
            Text(text = stringResource(R.string.sign_up_cta))
        } else {
            CircularProgressIndicator(modifier = Modifier.size(32.dp))
        }
    }
}
