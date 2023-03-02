package it.unibolss.smartparking.presentation.screens.parkingslot.parkingslots

import it.unibolss.smartparking.domain.entities.parkingslot.ParkingSlot

data class ParkingSlotsUiState(
    val parkingSlots: List<ParkingSlot>,
    val currentParkingSlot: ParkingSlot?,
    val loading: Boolean
) {
    companion object {
        fun initial() = ParkingSlotsUiState(
            parkingSlots = emptyList(),
            currentParkingSlot = null,
            loading = false
        )
    }
}
