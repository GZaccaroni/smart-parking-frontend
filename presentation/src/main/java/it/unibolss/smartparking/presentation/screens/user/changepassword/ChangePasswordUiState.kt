package it.unibolss.smartparking.presentation.screens.user.changepassword

/**
 * State of the change password screen UI.
 * @param currentPassword the current password entered by the user.
 * @param isCurrentPasswordError whether or not the current password entered by the user is not valid.
 * @param newPassword the new password entered by the user.
 * @param isNewPasswordError whether or not the new password entered by the user is not valid.
 * @param submitEnabled whether or not the submit button is enabled.
 * @param loading whether or not there is an ongoing operation.
 */
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
