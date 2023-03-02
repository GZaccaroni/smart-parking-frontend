package it.unibolss.smartparking.presentation.screens.parkingslot.parkingslot

import it.unibolss.smartparking.domain.entities.parkingslot.ParkingSlot

data class ParkingSlotUiState(
    val parkingSlot: ParkingSlot?,
    val loading: Boolean
) {
    companion object {
        fun initial() = ParkingSlotUiState(
            parkingSlot = null,
            loading = false
        )
    }
}
