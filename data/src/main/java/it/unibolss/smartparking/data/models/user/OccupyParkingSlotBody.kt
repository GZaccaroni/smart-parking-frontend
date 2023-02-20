package it.unibolss.smartparking.data.models.user

import kotlinx.datetime.Instant

internal data class OccupyParkingSlotBody(
    val stopEnd: Instant
)
