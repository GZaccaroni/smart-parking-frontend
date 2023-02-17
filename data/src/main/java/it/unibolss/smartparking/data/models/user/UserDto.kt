package it.unibolss.smartparking.data.models.user

import kotlinx.serialization.Serializable

@Serializable
internal data class UserDto(
    val name: String,
    val email: String,
)

