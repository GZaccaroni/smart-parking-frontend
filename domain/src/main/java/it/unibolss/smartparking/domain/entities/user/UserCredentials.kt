package it.unibolss.smartparking.domain.entities.user

/**
 * The credentials used by a user to enter the app
 * @param email the email of the user
 * @param password the password of the user
 */
data class UserCredentials(val email: String, val password: String)
