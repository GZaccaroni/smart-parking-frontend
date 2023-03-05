package it.unibolss.smartparking.data.models.parkingslot

import kotlinx.serialization.Serializable

@Serializable
internal data class GeoPositionDto(
    val longitude: Double,
    val latitude: Double
)

/*
@Serializable
internal data class Position(
    val longitude: Double,
    val latitude: Double
)
*/
