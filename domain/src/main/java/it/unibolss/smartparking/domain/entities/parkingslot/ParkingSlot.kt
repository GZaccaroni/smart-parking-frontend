package it.unibolss.smartparking.domain.entities.parkingslot

import it.unibolss.smartparking.domain.entities.geo.GeoPosition

data class ParkingSlot(
    val id: String,
    val position: GeoPosition,
)