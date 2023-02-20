package it.unibolss.smartparking.data.models.user

import kotlinx.serialization.Serializable

@Serializable
internal data class AuthenticationResult(
    val token: String
)
