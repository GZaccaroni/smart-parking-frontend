package it.unibolss.smartparking.presentation.screens.user.login

/**
 * State of the login screen UI.
 * @param email the email entered by the user.
 * @param isEmailError whether or not the email entered by the user is not valid.
 * @param password the password entered by the user.
 * @param isPasswordError whether or not the password entered by the user is not valid.
 * @param submitEnabled whether or not the submit button is enabled.
 * @param loading whether or not there is an ongoing operation.
 */
data class LoginUiState(
    val email: String,
    val isEmailError: Boolean,
    val password: String,
    val isPasswordError: Boolean,
    val submitEnabled: Boolean,
    val loading: Boolean
) {
    companion object {
        fun initial() = LoginUiState(
            email = "",
            isEmailError = false,
            password = "",
            isPasswordError = false,
            submitEnabled = false,
            loading = false
        )
    }
}
