package it.unibolss.smartparking.presentation.screens.changepassword

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
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
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
fun ChangePasswordScreen(
    vm: ChangePasswordScreenViewModel = koinViewModel()
) {
    val uiState by vm.uiState.collectAsState()
    val alertState by vm.alertState.collectAsState()

    ChangePasswordLayout(
        uiState = uiState,
        alertState = alertState,
        onCurrentPasswordChange = { vm.setCurrentPassword(it) },
        onNewPasswordChange = { vm.setNewPassword(it) },
        onSubmit = { vm.submit() },
        onBackClicked = { vm.goBack() }
    )
}

@Composable
@TestOnly
fun ChangePasswordLayout(
    uiState: ChangePasswordUiState,
    alertState: AppAlertState,
    onCurrentPasswordChange: (String) -> Unit,
    onNewPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onBackClicked: () -> Unit,
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    alertState.Bind(scaffoldState.snackbarHostState)

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.screen_title_change_password)) },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(Icons.Rounded.ArrowBack, stringResource(R.string.go_back_cta))
                    }
                },
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .padding(32.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(
                    10.dp,
                )
            ) {
                CurrentPasswordTextField(
                    uiState = uiState,
                    onCurrentPasswordChange = onCurrentPasswordChange
                )
                NewPasswordTextField(
                    uiState = uiState,
                    onNewPasswordChange = onNewPasswordChange
                )
                SubmitButton(
                    uiState = uiState,
                    onSubmit = onSubmit
                )
            }
        }
    }
}

@Composable
private fun SubmitButton(
    uiState: ChangePasswordUiState,
    onSubmit: () -> Unit
) {
    Button(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth(),
        onClick = onSubmit,
        enabled = uiState.submitEnabled && !uiState.loading,
    ) {
        if (!uiState.loading) {
            Text(text = stringResource(R.string.change_password_cta))
        } else {
            CircularProgressIndicator(modifier = Modifier.size(32.dp))
        }
    }
}

@Composable
private fun CurrentPasswordTextField(
    uiState: ChangePasswordUiState,
    onCurrentPasswordChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = uiState.currentPassword,
        onValueChange = onCurrentPasswordChange,
        enabled = !uiState.loading,
        leadingIcon = { Icon(Icons.Rounded.Lock, contentDescription = null) },
        label = { Text(stringResource(R.string.user_current_password)) },
        isError = uiState.isCurrentPasswordError,
        textStyle = TextStyle(fontSize = 18.sp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation(),
    )
}

@Composable
private fun NewPasswordTextField(
    uiState: ChangePasswordUiState,
    onNewPasswordChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = uiState.newPassword,
        onValueChange = onNewPasswordChange,
        enabled = !uiState.loading,
        leadingIcon = { Icon(Icons.Rounded.Lock, contentDescription = null) },
        label = { Text(stringResource(R.string.user_new_password)) },
        isError = uiState.isNewPasswordError,
        textStyle = TextStyle(fontSize = 18.sp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation(),
    )
}
