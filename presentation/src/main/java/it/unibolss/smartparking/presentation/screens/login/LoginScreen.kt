package it.unibolss.smartparking.presentation.screens.login

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import it.unibolss.smartparking.presentation.R
import it.unibolss.smartparking.presentation.common.snackbar.Bind
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun LoginScreen() {
    val vm = koinViewModel<LoginScreenViewModel>()
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    vm.snackbar.Bind(scaffoldState.snackbarHostState)

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
                    text = stringResource(R.string.login_screen_title),
                    fontSize = 40.sp,
                )

                EmailTextField(vm)
                PasswordTextField(vm)
                SubmitButton(vm)

                SignUpButton(vm)
            }
        }
    }
}

@Composable
private fun SignUpButton(
    vm: LoginScreenViewModel,
) {
    val loading by vm.loading.collectAsState()

    TextButton(
        modifier = Modifier.fillMaxWidth(),
        enabled = !loading,
        onClick = {
            vm.signUp()
        }
    ) {
        Text(stringResource(R.string.go_to_sign_up_cta), textAlign = TextAlign.Center)
    }
}

@Composable
private fun SubmitButton(
    vm: LoginScreenViewModel,
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
            Text(text = stringResource(R.string.login_cta))
        } else {
            CircularProgressIndicator(modifier = Modifier.size(32.dp))
        }
    }
}

@Composable
private fun EmailTextField(
    vm: LoginScreenViewModel,
) {
    val email by vm.email.collectAsState()
    val emailError by vm.emailError.collectAsState()
    val loading by vm.loading.collectAsState()

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = email,
        onValueChange = {
            vm.setEmail(it)
        },
        enabled = !loading,
        leadingIcon = { Icon(Icons.Rounded.Email, contentDescription = null) },
        label = { Text(stringResource(R.string.user_email)) },
        isError = emailError,
        textStyle = TextStyle(fontSize = 18.sp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )
}

@Composable
private fun PasswordTextField(
    vm: LoginScreenViewModel,
) {
    val password by vm.password.collectAsState()
    val passwordError by vm.passwordError.collectAsState()
    val loading by vm.loading.collectAsState()

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = password,
        onValueChange = {
            vm.setPassword(it)
        },
        enabled = !loading,
        leadingIcon = { Icon(Icons.Rounded.Lock, contentDescription = null) },
        label = { Text(stringResource(R.string.user_password)) },
        isError = passwordError,
        textStyle = TextStyle(fontSize = 18.sp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation(),
    )
}