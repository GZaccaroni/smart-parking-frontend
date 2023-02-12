package it.unibolss.smartparking.domain.entities.parkingslot

import java.util.Date

sealed class ParkingSlotState {
    object Free: ParkingSlotState()
    data class Occupied(val freesAt: Date) : ParkingSlotState()
}