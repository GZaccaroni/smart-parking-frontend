package it.unibolss.smartparking.domain.entities.user

/**
 * This entity represents a user
 * @param name the name of the user
 * @param email the email of the user
 */
data class User(
    val name: String,
    val email: String,
)
