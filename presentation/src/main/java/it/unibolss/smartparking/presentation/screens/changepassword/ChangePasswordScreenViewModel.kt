package it.unibolss.smartparking.presentation.screens.changepassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.unibolss.smartparking.domain.usecases.user.ChangeUserPassword
import it.unibolss.smartparking.domain.usecases.user.ValidateUserPassword
import it.unibolss.smartparking.presentation.R
import it.unibolss.smartparking.presentation.common.appalert.AppAlert
import it.unibolss.smartparking.presentation.navigation.Router
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

class ChangePasswordScreenViewModel(
    private val changeUserPassword: ChangeUserPassword,
    private val validateUserPassword: ValidateUserPassword,
    private val router: Router,
) : ViewModel() {

    private val _snackbar = MutableSharedFlow<AppAlert>(0)
    val snackbar: SharedFlow<AppAlert> = _snackbar.asSharedFlow()

    private val _currentPassword = MutableStateFlow("")
    val currentPassword: StateFlow<String> = _currentPassword.asStateFlow()

    private val _newPassword = MutableStateFlow("")
    val newPassword: StateFlow<String> = _newPassword.asStateFlow()

    val currentPasswordError: StateFlow<Boolean> =
        _currentPassword
            .drop(1)
            .map { !validateUserPassword(ValidateUserPassword.Params(it)) }
            .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    val newPasswordError: StateFlow<Boolean> =
        _newPassword
            .drop(1)
            .map { !validateUserPassword(ValidateUserPassword.Params(it)) }
            .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    val submitButtonEnabled: StateFlow<Boolean> =
        combine(currentPassword, newPassword) { currentPassword, newPassword ->
            val currentPasswordValid = validateUserPassword(ValidateUserPassword.Params(currentPassword))
            val newPasswordValid = validateUserPassword(ValidateUserPassword.Params(newPassword))
            currentPasswordValid && newPasswordValid
        }.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    fun setCurrentPassword(value: String) {
        _currentPassword.value = value
    }
    fun setNewPassword(value: String) {
        _newPassword.value = value
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
            val result = changeUserPassword(
                ChangeUserPassword.Params(
                    currentPassword = currentPassword.value,
                    newPassword = newPassword.value
                )
            )

            _loading.value = false
            result.fold(
                { _snackbar.emit(AppAlert.Error(it)) },
                {
                    _snackbar.emit(AppAlert.Text(R.string.app_success_change_password))
                    router.popBackStack()
                }
            )
        }
    }
    fun goBack() {
        router.popBackStack()
    }
}
