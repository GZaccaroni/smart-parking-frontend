package it.unibolss.smartparking.presentation.screens.user.signup

/**
 * State of the sign up screen UI.
 * @param email the email entered by the user.
 * @param isEmailError whether or not the email entered by the user is not valid.
 * @param name the name entered by the user.
 * @param isNameError whether or not the name entered by the user is not valid.
 * @param password the password entered by the user.
 * @param isPasswordError whether or not the password entered by the user is not valid.
 * @param submitEnabled whether or not the submit button is enabled.
 * @param loading whether or not there is an ongoing operation.
 */
data class SignUpUiState(
    val email: String,
    val isEmailError: Boolean,
    val name: String,
    val isNameError: Boolean,
    val password: String,
    val isPasswordError: Boolean,
    val submitEnabled: Boolean,
    val loading: Boolean
) {
    companion object {
        fun initial() = SignUpUiState(
            email = "",
            isEmailError = false,
            name = "",
            isNameError = false,
            password = "",
            isPasswordError = false,
            submitEnabled = false,
            loading = false
        )
    }
}
