package it.unibolss.smartparking.data.models.user

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
internal data class OccupyParkingSlotBody(
    val stopEnd: Instant
)
