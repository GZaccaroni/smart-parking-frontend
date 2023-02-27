package it.unibolss.smartparking.presentation.screens.login

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
