package it.unibolss.smartparking.presentation.screens.user.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import arrow.core.Either
import it.unibolss.smartparking.domain.usecases.user.SignUpUser
import it.unibolss.smartparking.domain.usecases.user.ValidateUserEmail
import it.unibolss.smartparking.domain.usecases.user.ValidateUserName
import it.unibolss.smartparking.domain.usecases.user.ValidateUserPassword
import it.unibolss.smartparking.presentation.common.appalert.AppAlert
import it.unibolss.smartparking.presentation.common.appalert.AppAlertState
import it.unibolss.smartparking.presentation.common.appalert.show
import it.unibolss.smartparking.presentation.navigation.Router
import it.unibolss.smartparking.presentation.screens.parkingslot.parkingslots.ParkingSlotsRoute
import it.unibolss.smartparking.presentation.screens.user.login.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * View Model of the sign up screen
 */
class SignUpScreenViewModel(
    private val signUpUser: SignUpUser,
    private val validateUserName: ValidateUserName,
    private val validateUserEmail: ValidateUserEmail,
    private val validateUserPassword: ValidateUserPassword,
    private val router: Router,
) : ViewModel() {

    private val _alertState = MutableStateFlow<AppAlertState>(AppAlertState.None)

    /**
     * Current state of UI alerts
     */
    val alertState: StateFlow<AppAlertState> = _alertState.asStateFlow()

    private val _uiState = MutableStateFlow(SignUpUiState.initial())

    /**
     * Current state of the UI
     */
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    /**
     * Sets the [name] to use for signing up the user with the [submit] method
     */
    fun setName(name: String) {
        val newName = name.trim()
        _uiState.value =
            _uiState.value.copy(name = newName).validated()
    }

    /**
     * Sets the [email] to use for signing up the user with the [submit] method
     */
    fun setEmail(email: String) {
        val newEmail = email.trim()
        _uiState.value =
            _uiState.value.copy(email = newEmail).validated()
    }

    /**
     * Sets the [password] to use for signing up the user with the [submit] method
     */
    fun setPassword(password: String) {
        _uiState.value =
            _uiState.value.copy(password = password).validated()
    }

    /**
     * Submits the login form using the name, email and password provided with
     * [setName], [setEmail] and [setPassword].
     * @throws IllegalStateException if [uiState] is loading ([LoginUiState.loading]) or submit
     * is not enabled ([LoginUiState.submitEnabled])
     */
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

            when (result) {
                is Either.Right -> {
                    router.navigateTo(
                        ParkingSlotsRoute,
                        NavOptions.Builder().setPopUpTo(0, true).build(),
                    )
                }
                is Either.Left -> {
                    _alertState.show(AppAlert.Error(result.value))
                }
            }
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
