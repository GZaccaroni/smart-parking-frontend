package it.unibolss.smartparking.data.models.user

import kotlinx.serialization.Serializable

@Serializable
internal data class ChangePasswordRequestBody(
    val currentPassword: String,
    val newPassword: String
)
