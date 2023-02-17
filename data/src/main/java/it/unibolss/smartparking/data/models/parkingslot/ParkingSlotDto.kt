package it.unibolss.smartparking.data.models.parkingslot

import java.util.Date

internal data class ParkingSlotDto(
    val id: String,
    val position: GeoPositionDto,
    val freesAt: Date?
)
