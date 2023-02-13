package it.unibolss.smartparking.domain.entities.user

data class NewUser(
    val name: String,
    val email: String,
    val password: String,
)