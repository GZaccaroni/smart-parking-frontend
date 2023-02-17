package it.unibolss.smartparking.data.models.user

import kotlinx.serialization.Serializable

@Serializable
internal data class SignUpRequestBody(
    val name: String,
    val email: String,
    val password: String
)
