package it.unibolss.smartparking.domain.entities.user

import it.unibolss.smartparking.domain.entities.geo.GeoPosition
import java.util.Date

data class UserParkingSlot(
    val id: String,
    val expiresAt: Date,
    val position: GeoPosition
)