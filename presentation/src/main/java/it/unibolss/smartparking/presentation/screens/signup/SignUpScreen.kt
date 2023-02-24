package it.unibolss.smartparking.presentation.screens.signup

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
import it.unibolss.smartparking.presentation.common.appalert.Bind
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignUpScreen() {
    val vm = koinViewModel<SignUpScreenViewModel>()
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    vm.snackbar.Bind(scaffoldState.snackbarHostState)

    Scaffold { paddingValues ->
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
                NameTextField(vm)
                EmailTextField(vm)
                PasswordTextField(vm)

                SignUpButton(vm)
            }
        }
    }
}

@Composable
private fun SignUpButton(vm: SignUpScreenViewModel) {
    val loading by vm.loading.collectAsState()
    val submitButtonEnabled by vm.submitButtonEnabled.collectAsState()
    Button(
        enabled = submitButtonEnabled && !loading,
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth(),
        onClick = {
            vm.submit()
        },
    ) {
        if (!loading) {
            Text(text = stringResource(R.string.sign_up_cta))
        } else {
            CircularProgressIndicator(modifier = Modifier.size(32.dp))
        }
    }
}

@Composable
private fun NameTextField(vm: SignUpScreenViewModel) {
    val name by vm.name.collectAsState()
    val nameError by vm.nameError.collectAsState()
    val loading by vm.loading.collectAsState()
    OutlinedTextField(
        value = name,
        isError = nameError,
        enabled = !loading,
        onValueChange = {
            vm.setName(it)
        },
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = { Icon(Icons.Rounded.Face, contentDescription = null) },
        label = { Text(stringResource(R.string.user_name)) },
        textStyle = TextStyle(fontSize = 18.sp),
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
    )
}

@Composable
private fun EmailTextField(vm: SignUpScreenViewModel) {
    val email by vm.email.collectAsState()
    val emailError by vm.emailError.collectAsState()
    val loading by vm.loading.collectAsState()
    OutlinedTextField(
        value = email,
        isError = emailError,
        enabled = !loading,
        onValueChange = {
            vm.setEmail(it)
        },
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = { Icon(Icons.Rounded.Email, contentDescription = null) },
        label = { Text(stringResource(R.string.user_email)) },

        textStyle = TextStyle(fontSize = 18.sp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )
}

@Composable
private fun PasswordTextField(vm: SignUpScreenViewModel) {
    val password by vm.password.collectAsState()
    val passwordError by vm.passwordError.collectAsState()
    val loading by vm.loading.collectAsState()
    OutlinedTextField(
        value = password,
        isError = passwordError,
        enabled = !loading,
        onValueChange = {
            vm.setPassword(it)
        },
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = { Icon(Icons.Rounded.Lock, contentDescription = null) },
        label = { Text(stringResource(R.string.user_password)) },

        textStyle = TextStyle(fontSize = 18.sp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation(),
    )
}
