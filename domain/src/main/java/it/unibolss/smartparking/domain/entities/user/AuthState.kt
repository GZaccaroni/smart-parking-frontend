package it.unibolss.smartparking.domain.entities.user

/**
 * The authentication state of the user: [LoggedIn] or [Guest]
 */
sealed interface AuthState {
    object LoggedIn : AuthState
    object Guest : AuthState
}
