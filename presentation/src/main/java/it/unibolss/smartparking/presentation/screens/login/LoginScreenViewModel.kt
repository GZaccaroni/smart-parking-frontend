package it.unibolss.smartparking.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import it.unibolss.smartparking.domain.usecases.user.LoginUser
import it.unibolss.smartparking.domain.usecases.user.ValidateUserEmail
import it.unibolss.smartparking.domain.usecases.user.ValidateUserPassword
import it.unibolss.smartparking.presentation.common.appalert.AppAlert
import it.unibolss.smartparking.presentation.navigation.Router
import it.unibolss.smartparking.presentation.screens.parkingslots.ParkingSlotsRoute
import it.unibolss.smartparking.presentation.screens.signup.SignUpRoute
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val loginUser: LoginUser,
    private val validateUserEmail: ValidateUserEmail,
    private val validateUserPassword: ValidateUserPassword,
    private val router: Router,
) : ViewModel() {

    private val _snackbar = MutableSharedFlow<AppAlert>(0)
    val snackbar: SharedFlow<AppAlert> = _snackbar.asSharedFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    val emailError: StateFlow<Boolean> =
        _email
            .drop(1)
            .map { !validateUserEmail(ValidateUserEmail.Params(it)) }
            .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    val passwordError: StateFlow<Boolean> =
        _password
            .drop(1)
            .map { !validateUserPassword(ValidateUserPassword.Params(it)) }
            .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    val submitButtonEnabled: StateFlow<Boolean> =
        combine(email, password) { email, password ->
            val emailValid = validateUserEmail(ValidateUserEmail.Params(email))
            val passwordValid = validateUserPassword(ValidateUserPassword.Params(password))
            emailValid && passwordValid
        }.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    fun setEmail(email: String) {
        _email.value = email.trim()
    }
    fun setPassword(password: String) {
        _password.value = password
    }

    fun submit() {
        check(submitButtonEnabled.value) {
            "Submit method should not be called if submitButtonEnabled is not true"
        }
        check(!loading.value) {
            "Submit method should not be called if loading is true"
        }
        viewModelScope.launch {
            _loading.value = true
            val result = loginUser(LoginUser.Params(email.value, password.value))

            _loading.value = false
            result.fold(
                { _snackbar.emit(AppAlert.Error(it)) },
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
}
