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
import it.unibolss.smartparking.presentation.common.appalert.Bind
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChangePasswordScreen(
    vm: ChangePasswordScreenViewModel = koinViewModel()
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    vm.snackbar.Bind(scaffoldState.snackbarHostState)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.screen_title_change_password)) },
                navigationIcon = {
                    IconButton(onClick = {
                        vm.goBack()
                    }) {
                        Icon(Icons.Rounded.ArrowBack, stringResource(R.string.go_back_cta))
                    }
                },
            )
        },
        scaffoldState = scaffoldState
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
                CurrentPasswordTextField(vm)
                NewPasswordTextField(vm)
                SubmitButton(vm)
            }
        }
    }
}

@Composable
private fun SubmitButton(
    vm: ChangePasswordScreenViewModel,
) {
    val submitButtonEnabled by vm.submitButtonEnabled.collectAsState()
    val loading by vm.loading.collectAsState()

    Button(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth(),
        onClick = {
            vm.submit()
        },
        enabled = submitButtonEnabled && !loading,
    ) {
        if (!loading) {
            Text(text = stringResource(R.string.change_password_cta))
        } else {
            CircularProgressIndicator(modifier = Modifier.size(32.dp))
        }
    }
}

@Composable
private fun CurrentPasswordTextField(
    vm: ChangePasswordScreenViewModel,
) {
    val currentPassword by vm.currentPassword.collectAsState()
    val currentPasswordError by vm.currentPasswordError.collectAsState()
    val loading by vm.loading.collectAsState()

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = currentPassword,
        onValueChange = {
            vm.setCurrentPassword(it)
        },
        enabled = !loading,
        leadingIcon = { Icon(Icons.Rounded.Lock, contentDescription = null) },
        label = { Text(stringResource(R.string.user_current_password)) },
        isError = currentPasswordError,
        textStyle = TextStyle(fontSize = 18.sp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation(),
    )
}

@Composable
private fun NewPasswordTextField(
    vm: ChangePasswordScreenViewModel,
) {
    val newPassword by vm.newPassword.collectAsState()
    val newPasswordError by vm.newPasswordError.collectAsState()
    val loading by vm.loading.collectAsState()

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = newPassword,
        onValueChange = {
            vm.setNewPassword(it)
        },
        enabled = !loading,
        leadingIcon = { Icon(Icons.Rounded.Lock, contentDescription = null) },
        label = { Text(stringResource(R.string.user_new_password)) },
        isError = newPasswordError,
        textStyle = TextStyle(fontSize = 18.sp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation(),
    )
}
