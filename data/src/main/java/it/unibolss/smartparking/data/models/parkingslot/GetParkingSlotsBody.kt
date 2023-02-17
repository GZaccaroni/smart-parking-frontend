package it.unibolss.smartparking.data.models.parkingslot

import kotlinx.serialization.Serializable

@Serializable
internal data class GetParkingSlotsBody(
    val currentPassword: String,
    val newPassword: String
)
