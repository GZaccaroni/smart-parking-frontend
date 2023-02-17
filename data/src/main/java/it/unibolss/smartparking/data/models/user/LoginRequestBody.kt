package it.unibolss.smartparking.data.models.user

import kotlinx.serialization.Serializable

@Serializable
internal data class LoginRequestBody(
    val email: String,
    val password: String
)
