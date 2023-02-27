package it.unibolss.smartparking.presentation.screens.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import it.unibolss.smartparking.domain.usecases.user.SignUpUser
import it.unibolss.smartparking.domain.usecases.user.ValidateUserEmail
import it.unibolss.smartparking.domain.usecases.user.ValidateUserName
import it.unibolss.smartparking.domain.usecases.user.ValidateUserPassword
import it.unibolss.smartparking.presentation.common.appalert.AppAlert
import it.unibolss.smartparking.presentation.common.appalert.AppAlertState
import it.unibolss.smartparking.presentation.common.appalert.show
import it.unibolss.smartparking.presentation.navigation.Router
import it.unibolss.smartparking.presentation.screens.parkingslots.ParkingSlotsRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignUpScreenViewModel(
    private val signUpUser: SignUpUser,
    private val validateUserName: ValidateUserName,
    private val validateUserEmail: ValidateUserEmail,
    private val validateUserPassword: ValidateUserPassword,
    private val router: Router,
) : ViewModel() {

    private val _alertState = MutableStateFlow<AppAlertState>(AppAlertState.None)
    val alertState: StateFlow<AppAlertState> = _alertState.asStateFlow()

    private val _uiState = MutableStateFlow(SignUpUiState.initial())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    fun setName(name: String) {
        val newName = name.trim()
        _uiState.value =
            _uiState.value.copy(name = newName).validated()
    }
    fun setEmail(email: String) {
        val newEmail = email.trim()
        _uiState.value =
            _uiState.value.copy(email = newEmail).validated()
    }
    fun setPassword(password: String) {
        _uiState.value =
            _uiState.value.copy(password = password).validated()
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
            _uiState.value = uiState.value.copy(loading = true)
            val result = signUpUser(
                SignUpUser.Params(
                    name = currentUiState.name,
                    email = currentUiState.email,
                    password = currentUiState.password
                )
            )

            _uiState.value = uiState.value.copy(loading = false)
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

    private fun SignUpUiState.validated(): SignUpUiState {
        val nameValid = validateUserName(ValidateUserName.Params(name))
        val emailValid = validateUserEmail(ValidateUserEmail.Params(email))
        val passwordValid = validateUserPassword(ValidateUserPassword.Params(password))
        return this.copy(
            isNameError = name.isNotEmpty() && !nameValid,
            isEmailError = email.isNotEmpty() && !emailValid,
            isPasswordError = password.isNotEmpty() && !passwordValid,
            submitEnabled = nameValid && emailValid && passwordValid
        )
    }
}
