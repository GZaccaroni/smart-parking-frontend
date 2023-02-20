package it.unibolss.smartparking.data.models.user

import kotlinx.datetime.Instant

internal data class IncrementParkingSlotOccupationBody(
    val stopEnd: Instant
)