package it.unibolss.smartparking.presentation.screens.changepassword

data class ChangePasswordUiState(
    val currentPassword: String,
    val isCurrentPasswordError: Boolean,
    val newPassword: String,
    val isNewPasswordError: Boolean,
    val submitEnabled: Boolean,
    val loading: Boolean
) {
    companion object {
        fun initial() = ChangePasswordUiState(
            currentPassword = "",
            isCurrentPasswordError = false,
            newPassword = "",
            isNewPasswordError = false,
            submitEnabled = false,
            loading = false
        )
    }
}
