package it.unibolss.smartparking.data.models.user

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
internal data class IncrementParkingSlotOccupationBody(
    val stopEnd: Instant
)
