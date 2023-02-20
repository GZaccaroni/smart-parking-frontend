package it.unibolss.smartparking.data.models.parkingslot

import kotlinx.serialization.Serializable

@Serializable
internal data class GetParkingSlotsBody(
    val radius: Double,
    val center: GeoPositionDto
)
