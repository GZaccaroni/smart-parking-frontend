package it.unibolss.smartparking.domain.entities.user

/**
 * The authentication state of the user: [LoggedIn] or [Guest]
 */
sealed interface AuthState {
    data class LoggedIn(val id: String): AuthState
    object Guest: AuthState
}
