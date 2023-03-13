package it.unibolss.smartparking.domain.entities.user

/**
 * This entity represents a new user to be created
 * @param name the name of the new user
 * @param email the email of the new user
 * @param password the password of the new user
 */
data class NewUser(
    val name: String,
    val email: String,
    val password: String,
)
