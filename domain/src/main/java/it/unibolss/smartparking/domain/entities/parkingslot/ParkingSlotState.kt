package it.unibolss.smartparking.domain.entities.parkingslot

import kotlinx.datetime.Instant

sealed interface ParkingSlotState {
    object Free : ParkingSlotState
    data class Occupied(val freesAt: Instant) : ParkingSlotState
}