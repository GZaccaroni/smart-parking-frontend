package it.unibolss.smartparking.data.models.user

internal data class AuthenticationInfo(
    val userId: String,
    val authToken: String,
)
