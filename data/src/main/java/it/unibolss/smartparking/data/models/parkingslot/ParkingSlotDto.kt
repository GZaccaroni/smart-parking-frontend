package it.unibolss.smartparking.data.models.parkingslot

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
internal data class ParkingSlotDto(
    val id: String,
    val position: GeoPositionDto,
    val occupied: Boolean,
    val stopEnd: Instant?
)
