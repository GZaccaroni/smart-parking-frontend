package it.unibolss.smartparking.presentation.screens.parkingslot.parkingslots

import it.unibolss.smartparking.domain.entities.parkingslot.ParkingSlot

/**
 * State of the UI of a screen displaying a group of parking slots ([parkingSlots]).
 * @param parkingSlots the parking slots to be shown to the user.
 * @param currentParkingSlot is the current parking slot occupied by the user.
 * @param loading whether the data are still loading.
 */
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
