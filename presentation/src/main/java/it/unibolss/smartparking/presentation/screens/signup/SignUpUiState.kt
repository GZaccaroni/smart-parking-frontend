package it.unibolss.smartparking.presentation.screens.signup

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
