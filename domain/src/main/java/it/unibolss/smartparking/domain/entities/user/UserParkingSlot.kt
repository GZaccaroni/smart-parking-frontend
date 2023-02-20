package it.unibolss.smartparking.domain.entities.user

import it.unibolss.smartparking.domain.entities.geo.GeoPosition
import kotlinx.datetime.Instant

data class UserParkingSlot(
    val id: String,
    val expiresAt: Instant,
    val position: GeoPosition
)
