package it.unibolss.smartparking.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import it.unibolss.smartparking.domain.usecases.user.LoginUser
import it.unibolss.smartparking.domain.usecases.user.ValidateUserEmail
import it.unibolss.smartparking.domain.usecases.user.ValidateUserPassword
import it.unibolss.smartparking.presentation.common.appalert.AppAlert
import it.unibolss.smartparking.presentation.common.appalert.AppAlertState
import it.unibolss.smartparking.presentation.common.appalert.show
import it.unibolss.smartparking.presentation.navigation.Router
import it.unibolss.smartparking.presentation.screens.parkingslots.ParkingSlotsRoute
import it.unibolss.smartparking.presentation.screens.signup.SignUpRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val loginUser: LoginUser,
    private val validateUserEmail: ValidateUserEmail,
    private val validateUserPassword: ValidateUserPassword,
    private val router: Router,
) : ViewModel() {

    private val _alertState = MutableStateFlow<AppAlertState>(AppAlertState.None)
    val alertState: StateFlow<AppAlertState> = _alertState.asStateFlow()

    private val _uiState = MutableStateFlow(LoginUiState.initial())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun setEmail(value: String) {
        val trimmedValue = value.trim()
        _uiState.value = _uiState.value.copy(email = trimmedValue).validated()
    }
    fun setPassword(value: String) {
        _uiState.value = _uiState.value.copy(password = value).validated()
    }

    fun submit() {
        val currentUiState = _uiState.value
        check(currentUiState.submitEnabled) {
            "Submit method should not be called if submitButtonEnabled is not true"
        }
        check(!currentUiState.loading) {
            "Submit method should not be called if loading is true"
        }
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loading = true)
            val result = loginUser(
                LoginUser.Params(
                    currentUiState.email,
                    currentUiState.password
                )
            )

            _uiState.value = _uiState.value.copy(loading = false)
            result.fold(
                { _alertState.show(AppAlert.Error(it)) },
                {
                    router.navigateTo(
                        ParkingSlotsRoute,
                        NavOptions.Builder().setPopUpTo(0, true).build(),
                    )
                }
            )
        }
    }
    fun signUp() {
        router.navigateTo(SignUpRoute)
    }

    private fun LoginUiState.validated(): LoginUiState {
        val emailValid = validateUserEmail(ValidateUserEmail.Params(email))
        val passwordValid = validateUserPassword(ValidateUserPassword.Params(password))
        return this.copy(
            isEmailError = email.isNotEmpty() && !emailValid,
            isPasswordError = password.isNotEmpty() && !passwordValid,
            submitEnabled = emailValid && passwordValid
        )
    }
}
