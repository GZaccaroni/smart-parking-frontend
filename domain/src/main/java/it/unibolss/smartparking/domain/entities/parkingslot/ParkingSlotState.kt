package it.unibolss.smartparking.domain.entities.parkingslot

import java.util.Date

sealed class ParkingSlotState {
    object FreeParkingSlotState : ParkingSlotState()
    data class OccupiedParkingSlotState(val freesAt: Date) : ParkingSlotState()
}