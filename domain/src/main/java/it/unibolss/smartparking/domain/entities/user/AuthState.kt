package it.unibolss.smartparking.domain.entities.user

sealed class AuthState {
    data class LoggedIn(val id: String): AuthState()
    object LoggedOut: AuthState()
}
