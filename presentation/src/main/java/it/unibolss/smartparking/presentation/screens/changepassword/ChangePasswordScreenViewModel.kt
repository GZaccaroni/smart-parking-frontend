package it.unibolss.smartparking.presentation.screens.changepassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.unibolss.smartparking.domain.usecases.user.ChangeUserPassword
import it.unibolss.smartparking.domain.usecases.user.ValidateUserPassword
import it.unibolss.smartparking.presentation.R
import it.unibolss.smartparking.presentation.common.appalert.AppAlert
import it.unibolss.smartparking.presentation.common.appalert.AppAlertState
import it.unibolss.smartparking.presentation.common.appalert.show
import it.unibolss.smartparking.presentation.navigation.Router
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChangePasswordScreenViewModel(
    private val changeUserPassword: ChangeUserPassword,
    private val validateUserPassword: ValidateUserPassword,
    private val router: Router,
) : ViewModel() {

    private val _alertState = MutableStateFlow<AppAlertState>(AppAlertState.None)
    val alertState: StateFlow<AppAlertState> = _alertState.asStateFlow()

    private val _uiState = MutableStateFlow(ChangePasswordUiState.initial())
    val uiState: StateFlow<ChangePasswordUiState> = _uiState.asStateFlow()

    fun setCurrentPassword(value: String) {
        _uiState.value = _uiState.value.copy(currentPassword = value).validated()
    }
    fun setNewPassword(value: String) {
        _uiState.value = _uiState.value.copy(newPassword = value).validated()
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
            val result = changeUserPassword(
                ChangeUserPassword.Params(
                    currentPassword = currentUiState.currentPassword,
                    newPassword = currentUiState.newPassword
                )
            )

            _uiState.value = _uiState.value.copy(loading = false)
            result.fold(
                { _alertState.show(AppAlert.Error(it)) },
                {
                    _alertState.show(AppAlert.Text(R.string.app_success_change_password))
                    router.popBackStack()
                }
            )
        }
    }
    fun goBack() {
        router.popBackStack()
    }

    private fun ChangePasswordUiState.validated(): ChangePasswordUiState {
        val currentPasswordValid = validateUserPassword(ValidateUserPassword.Params(currentPassword))
        val newPasswordValid = validateUserPassword(ValidateUserPassword.Params(newPassword))
        return this.copy(
            isCurrentPasswordError = !currentPasswordValid,
            isNewPasswordError = !newPasswordValid,
            submitEnabled = currentPasswordValid && newPasswordValid
        )
    }
}
