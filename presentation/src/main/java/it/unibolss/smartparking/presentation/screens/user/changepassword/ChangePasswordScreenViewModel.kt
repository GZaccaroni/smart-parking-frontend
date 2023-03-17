package it.unibolss.smartparking.presentation.screens.user.changepassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import it.unibolss.smartparking.domain.usecases.user.ChangeUserPassword
import it.unibolss.smartparking.domain.usecases.user.ValidateUserPassword
import it.unibolss.smartparking.presentation.R
import it.unibolss.smartparking.presentation.common.appalert.AppAlert
import it.unibolss.smartparking.presentation.common.appalert.AppAlertState
import it.unibolss.smartparking.presentation.common.appalert.show
import it.unibolss.smartparking.presentation.common.error.handleAppError
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

    /**
     * Current state of UI alerts
     */
    val alertState: StateFlow<AppAlertState> = _alertState.asStateFlow()

    private val _uiState = MutableStateFlow(ChangePasswordUiState.initial())

    /**
     * Current state of the UI
     */
    val uiState: StateFlow<ChangePasswordUiState> = _uiState.asStateFlow()

    /**
     * Sets the current password ([currentPassword]) used by the user to allow the change of
     * password with the [submit] method
     */
    fun setCurrentPassword(currentPassword: String) {
        _uiState.value = _uiState.value.copy(currentPassword = currentPassword).validated()
    }

    /**
     * Sets the new password ([newPassword]) wanted by the user to allow the change of
     * password with the [submit] method
     */
    fun setNewPassword(newPassword: String) {
        _uiState.value = _uiState.value.copy(newPassword = newPassword).validated()
    }

    /**
     * Submits the change password form using the current user password and the new password provided with
     * [setCurrentPassword] and [setNewPassword].
     * @throws IllegalStateException if [uiState] is loading ([ChangePasswordUiState.loading]) or submit
     * is not enabled ([ChangePasswordUiState.submitEnabled])
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
            _uiState.value = _uiState.value.copy(loading = true)
            val result = changeUserPassword(
                ChangeUserPassword.Params(
                    currentPassword = currentUiState.currentPassword,
                    newPassword = currentUiState.newPassword
                )
            )

            _uiState.value = _uiState.value.copy(loading = false)
            when (result) {
                is Either.Right -> {
                    router.popBackStack()
                    _alertState.show(AppAlert.Text(R.string.app_success_change_password))
                }
                is Either.Left -> {
                    handleAppError(result.value, _alertState, router)
                }
            }
        }
    }

    /**
     * Goes back to the previous screen
     * @throws IllegalStateException if [uiState] is loading ([ChangePasswordUiState.loading])
     */
    fun goBack() {
        check(!uiState.value.loading) {
            "goBack method should not be called if loading is true"
        }
        router.popBackStack()
    }

    private fun ChangePasswordUiState.validated(): ChangePasswordUiState {
        val currentPasswordValid = validateUserPassword(ValidateUserPassword.Params(currentPassword))
        val newPasswordValid = validateUserPassword(ValidateUserPassword.Params(newPassword))
        return this.copy(
            isCurrentPasswordError = currentPassword.isNotEmpty() && !currentPasswordValid,
            isNewPasswordError = newPassword.isNotEmpty() && !newPasswordValid,
            submitEnabled = currentPasswordValid && newPasswordValid
        )
    }
}
